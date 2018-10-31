package com.lzl.lambada.chapter5;

import java.nio.ByteBuffer;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 分割迭代器 通常会根据要分隔的数据结构而命名
 *
 */
public class LineSpliterator implements Spliterator<DisplayLine> {
    private ByteBuffer bb;
    private int lo,hi;

    public LineSpliterator(ByteBuffer bb, int lo, int hi) {
        this.bb = bb;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public boolean tryAdvance(Consumer<? super DisplayLine> action) {
        int index = lo;
        StringBuilder stringBuilder = new StringBuilder();
        do{
            stringBuilder.append((char)bb.get(index));
        }while (bb.get(index++) != '\n');
        action.accept(new DisplayLine(lo, stringBuilder.toString()));
        lo = lo + stringBuilder.length();
        return lo <= hi;
    }

    @Override
    public Spliterator<DisplayLine> trySplit() {
        int mid = (lo + hi) >>> 2;
        // 从中间往末尾找，这里有个问题，如果在 lo 与 mid之间有 换行符 的话
        while (bb.get(mid) != '\n')
        {
            mid++;
        }
        LineSpliterator lineSpliterator = null;
        // 如果没有到末尾，那就分隔一下
        if(mid != hi){
            lineSpliterator = new LineSpliterator(bb,lo,mid);
            lo = mid+1;
        }
        return lineSpliterator;
    }

    @Override
    public long estimateSize() {
        return (hi-lo+1);
    }

    @Override
    public int characteristics() {
        return ORDERED | IMMUTABLE | NONNULL;
    }
}
