package ninja.thepurple.groblins.common.entity;

import net.minecraft.util.math.BlockPos;
import ninja.thepurple.groblins.common.entity.groblin.helpers.ExpandingChunkSearch;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ExpandingChunkSearchTest {
    @Test
    public void firstIterationIsGood() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(8, 0, 8)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(8, 0, 8), ecs.next());
    }

    @Test
    public void worksAwayFromOrigin() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(17, 0, 17)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(17, 0, 17), ecs.next());

        assertEquals(new BlockPos(18, 0, 18), ecs.next());
        assertEquals(new BlockPos(17, 0, 18), ecs.next());
        assertEquals(new BlockPos(16, 0, 18), ecs.next());
        assertEquals(new BlockPos(16, 0, 17), ecs.next());
        assertEquals(new BlockPos(16, 0, 16), ecs.next());
        assertEquals(new BlockPos(17, 0, 16), ecs.next());
        assertEquals(new BlockPos(18, 0, 16), ecs.next());
        assertEquals(new BlockPos(18, 0, 17), ecs.next());

        assertEquals(new BlockPos(19, 0, 19), ecs.next());
        assertEquals(new BlockPos(18, 0, 19), ecs.next());
        assertEquals(new BlockPos(17, 0, 19), ecs.next());
        assertEquals(new BlockPos(16, 0, 19), ecs.next());
//        assertEquals(new BlockPos(15, 0, 19), ecs.next());
//        assertEquals(new BlockPos(15, 0, 18), ecs.next());
//        assertEquals(new BlockPos(15, 0, 17), ecs.next());
//        assertEquals(new BlockPos(15, 0, 16), ecs.next());
//        assertEquals(new BlockPos(15, 0, 15), ecs.next());
//        assertEquals(new BlockPos(16, 0, 15), ecs.next());
//        assertEquals(new BlockPos(17, 0, 15), ecs.next());
//        assertEquals(new BlockPos(18, 0, 15), ecs.next());
//        assertEquals(new BlockPos(19, 0, 15), ecs.next());
        assertEquals(new BlockPos(19, 0, 16), ecs.next());
        assertEquals(new BlockPos(19, 0, 17), ecs.next());
        assertEquals(new BlockPos(19, 0, 18), ecs.next());


    }

    @Test
    public void firstRadiusIterationIsGood() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(8, 0, 8)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(8, 0, 8), ecs.next());
        assertEquals(new BlockPos(9, 0, 9), ecs.next());
        assertEquals(new BlockPos(8, 0, 9), ecs.next());
        assertEquals(new BlockPos(7, 0, 9), ecs.next());
        assertEquals(new BlockPos(7, 0, 8), ecs.next());
        assertEquals(new BlockPos(7, 0, 7), ecs.next());
        assertEquals(new BlockPos(8, 0, 7), ecs.next());
        assertEquals(new BlockPos(9, 0, 7), ecs.next());
        assertEquals(new BlockPos(9, 0, 8), ecs.next());
    }

    @Test
    public void secondRadiusIterationIsGood() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(8, 0, 8)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(8, 0, 8), ecs.next());
        assertEquals(new BlockPos(9, 0, 9), ecs.next());
        assertEquals(new BlockPos(8, 0, 9), ecs.next());
        assertEquals(new BlockPos(7, 0, 9), ecs.next());
        assertEquals(new BlockPos(7, 0, 8), ecs.next());
        assertEquals(new BlockPos(7, 0, 7), ecs.next());
        assertEquals(new BlockPos(8, 0, 7), ecs.next());
        assertEquals(new BlockPos(9, 0, 7), ecs.next());
        assertEquals(new BlockPos(9, 0, 8), ecs.next());
        assertEquals(new BlockPos(10, 0, 10), ecs.next());
    }

    @Test
    public void thirdRadiusIterationIsGood() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(4, 0, 4)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(4, 0, 4), ecs.next());

        assertEquals(new BlockPos(5, 0, 5), ecs.next());
        assertEquals(new BlockPos(4, 0, 5), ecs.next());
        assertEquals(new BlockPos(3, 0, 5), ecs.next());
        assertEquals(new BlockPos(3, 0, 4), ecs.next());
        assertEquals(new BlockPos(3, 0, 3), ecs.next());
        assertEquals(new BlockPos(4, 0, 3), ecs.next());
        assertEquals(new BlockPos(5, 0, 3), ecs.next());
        assertEquals(new BlockPos(5, 0, 4), ecs.next());

        assertEquals(new BlockPos(6, 0, 6), ecs.next());
        assertEquals(new BlockPos(5, 0, 6), ecs.next());
        assertEquals(new BlockPos(4, 0, 6), ecs.next());
        assertEquals(new BlockPos(3, 0, 6), ecs.next());
        assertEquals(new BlockPos(2, 0, 6), ecs.next());
        assertEquals(new BlockPos(2, 0, 5), ecs.next());
        assertEquals(new BlockPos(2, 0, 4), ecs.next());
        assertEquals(new BlockPos(2, 0, 3), ecs.next());
        assertEquals(new BlockPos(2, 0, 2), ecs.next());
        assertEquals(new BlockPos(3, 0, 2), ecs.next());
        assertEquals(new BlockPos(4, 0, 2), ecs.next());
        assertEquals(new BlockPos(5, 0, 2), ecs.next());
        assertEquals(new BlockPos(6, 0, 2), ecs.next());
        assertEquals(new BlockPos(6, 0, 3), ecs.next());
        assertEquals(new BlockPos(6, 0, 4), ecs.next());
        assertEquals(new BlockPos(6, 0, 5), ecs.next());

        assertEquals(new BlockPos(7, 0, 7), ecs.next());
    }

    @Test
    public void fourthRadiusIterationIsGood() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(4, 0, 4)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(4, 0, 4), ecs.next());

        assertEquals(new BlockPos(5, 0, 5), ecs.next());
        assertEquals(new BlockPos(4, 0, 5), ecs.next());
        assertEquals(new BlockPos(3, 0, 5), ecs.next());
        assertEquals(new BlockPos(3, 0, 4), ecs.next());
        assertEquals(new BlockPos(3, 0, 3), ecs.next());
        assertEquals(new BlockPos(4, 0, 3), ecs.next());
        assertEquals(new BlockPos(5, 0, 3), ecs.next());
        assertEquals(new BlockPos(5, 0, 4), ecs.next());

        assertEquals(new BlockPos(6, 0, 6), ecs.next());
        assertEquals(new BlockPos(5, 0, 6), ecs.next());
        assertEquals(new BlockPos(4, 0, 6), ecs.next());
        assertEquals(new BlockPos(3, 0, 6), ecs.next());
        assertEquals(new BlockPos(2, 0, 6), ecs.next());
        assertEquals(new BlockPos(2, 0, 5), ecs.next());
        assertEquals(new BlockPos(2, 0, 4), ecs.next());
        assertEquals(new BlockPos(2, 0, 3), ecs.next());
        assertEquals(new BlockPos(2, 0, 2), ecs.next());
        assertEquals(new BlockPos(3, 0, 2), ecs.next());
        assertEquals(new BlockPos(4, 0, 2), ecs.next());
        assertEquals(new BlockPos(5, 0, 2), ecs.next());
        assertEquals(new BlockPos(6, 0, 2), ecs.next());
        assertEquals(new BlockPos(6, 0, 3), ecs.next());
        assertEquals(new BlockPos(6, 0, 4), ecs.next());
        assertEquals(new BlockPos(6, 0, 5), ecs.next());

        assertEquals(new BlockPos(7, 0, 7), ecs.next());
        assertEquals(new BlockPos(6, 0, 7), ecs.next());
        assertEquals(new BlockPos(5, 0, 7), ecs.next());
        assertEquals(new BlockPos(4, 0, 7), ecs.next());
        assertEquals(new BlockPos(3, 0, 7), ecs.next());
        assertEquals(new BlockPos(2, 0, 7), ecs.next());
        assertEquals(new BlockPos(1, 0, 7), ecs.next());
        assertEquals(new BlockPos(1, 0, 6), ecs.next());
        assertEquals(new BlockPos(1, 0, 5), ecs.next());
        assertEquals(new BlockPos(1, 0, 4), ecs.next());
        assertEquals(new BlockPos(1, 0, 3), ecs.next());
        assertEquals(new BlockPos(1, 0, 2), ecs.next());
        assertEquals(new BlockPos(1, 0, 1), ecs.next());
        assertEquals(new BlockPos(2, 0, 1), ecs.next());
        assertEquals(new BlockPos(3, 0, 1), ecs.next());
        assertEquals(new BlockPos(4, 0, 1), ecs.next());
        assertEquals(new BlockPos(5, 0, 1), ecs.next());
        assertEquals(new BlockPos(6, 0, 1), ecs.next());
        assertEquals(new BlockPos(7, 0, 1), ecs.next());
        assertEquals(new BlockPos(7, 0, 2), ecs.next());
        assertEquals(new BlockPos(7, 0, 3), ecs.next());
        assertEquals(new BlockPos(7, 0, 4), ecs.next());
        assertEquals(new BlockPos(7, 0, 5), ecs.next());
        assertEquals(new BlockPos(7, 0, 6), ecs.next());

        assertEquals(new BlockPos(8, 0, 8), ecs.next());
    }

 @Test
    public void willNotReturnBlocksOutsideChunk() throws Exception {
     Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(14, 0, 14)).iterator();

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(14, 0, 14), ecs.next());

        assertEquals(new BlockPos(15, 0, 15), ecs.next());
        assertEquals(new BlockPos(14, 0, 15), ecs.next());
        assertEquals(new BlockPos(13, 0, 15), ecs.next());
        assertEquals(new BlockPos(13, 0, 14), ecs.next());
        assertEquals(new BlockPos(13, 0, 13), ecs.next());
        assertEquals(new BlockPos(14, 0, 13), ecs.next());
        assertEquals(new BlockPos(15, 0, 13), ecs.next());
        assertEquals(new BlockPos(15, 0, 14), ecs.next());

//        assertEquals(new BlockPos(16, 0, 16), ecs.next());
//        assertEquals(new BlockPos(15, 0, 16), ecs.next());
//        assertEquals(new BlockPos(14, 0, 16), ecs.next());
//        assertEquals(new BlockPos(13, 0, 16), ecs.next());
//        assertEquals(new BlockPos(12, 0, 16), ecs.next());
        assertEquals(new BlockPos(12, 0, 15), ecs.next());
        assertEquals(new BlockPos(12, 0, 14), ecs.next());
        assertEquals(new BlockPos(12, 0, 13), ecs.next());
        assertEquals(new BlockPos(12, 0, 12), ecs.next());
        assertEquals(new BlockPos(13, 0, 12), ecs.next());
        assertEquals(new BlockPos(14, 0, 12), ecs.next());
        assertEquals(new BlockPos(15, 0, 12), ecs.next());
//        assertEquals(new BlockPos(16, 0, 12), ecs.next());
//        assertEquals(new BlockPos(16, 0, 13), ecs.next());
//        assertEquals(new BlockPos(16, 0, 14), ecs.next());
//        assertEquals(new BlockPos(16, 0, 15), ecs.next());

//        assertEquals(new BlockPos(17, 0, 17), ecs.next());
//        assertEquals(new BlockPos(16, 0, 17), ecs.next());
//        assertEquals(new BlockPos(15, 0, 17), ecs.next());
//        assertEquals(new BlockPos(14, 0, 17), ecs.next());
//        assertEquals(new BlockPos(13, 0, 17), ecs.next());
//        assertEquals(new BlockPos(12, 0, 17), ecs.next());
//        assertEquals(new BlockPos(11, 0, 17), ecs.next());
//        assertEquals(new BlockPos(11, 0, 16), ecs.next());
        assertEquals(new BlockPos(11, 0, 15), ecs.next());
        assertEquals(new BlockPos(11, 0, 14), ecs.next());
        assertEquals(new BlockPos(11, 0, 13), ecs.next());
        assertEquals(new BlockPos(11, 0, 12), ecs.next());
        assertEquals(new BlockPos(11, 0, 11), ecs.next());
        assertEquals(new BlockPos(12, 0, 11), ecs.next());
        assertEquals(new BlockPos(13, 0, 11), ecs.next());
        assertEquals(new BlockPos(14, 0, 11), ecs.next());
        assertEquals(new BlockPos(15, 0, 11), ecs.next());
//        assertEquals(new BlockPos(16, 0, 11), ecs.next());
//        assertEquals(new BlockPos(17, 0, 11), ecs.next());
//        assertEquals(new BlockPos(17, 0, 12), ecs.next());
//        assertEquals(new BlockPos(17, 0, 13), ecs.next());
//        assertEquals(new BlockPos(17, 0, 14), ecs.next());
//        assertEquals(new BlockPos(17, 0, 15), ecs.next());
//        assertEquals(new BlockPos(17, 0, 16), ecs.next());
    }

    @Test
    public void stopsWhenChunkIsFull() throws Exception {
        Iterator<BlockPos> ecs = new ExpandingChunkSearch(new BlockPos(7, 0, 7)).iterator();

        assertEquals(true, ecs.hasNext());

        assertEquals(new BlockPos(0x7, 0, 0x7), ecs.next());

        assertEquals(new BlockPos(0x8, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x7), ecs.next());

        assertEquals(new BlockPos(0x9, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x8), ecs.next());

        assertEquals(new BlockPos(0xa, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x9), ecs.next());

        assertEquals(new BlockPos(0xb, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0xa), ecs.next());

        assertEquals(new BlockPos(0xc, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0xb), ecs.next());

        assertEquals(new BlockPos(0xd, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0xc), ecs.next());

        assertEquals(new BlockPos(0xe, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xe), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xd), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0xd), ecs.next());

        assertEquals(new BlockPos(0xf, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xe, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xd, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xc, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xb, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xa, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x9, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x8, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x7, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x6, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x5, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x4, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x3, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x2, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x1, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0x0, 0, 0xf), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x0), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x1), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x2), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x3), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x4), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x5), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x6), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x7), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x8), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0x9), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0xa), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0xb), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0xc), ecs.next());
        assertEquals(new BlockPos(0xf, 0, 0xd), ecs.next());

        assertEquals(true, ecs.hasNext());
        assertEquals(new BlockPos(0xf, 0, 0xe), ecs.next());

        assertEquals(false, ecs.hasNext());
        assertEquals(null, ecs.next());



    }

}