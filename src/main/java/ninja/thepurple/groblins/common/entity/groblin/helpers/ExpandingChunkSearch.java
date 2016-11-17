package ninja.thepurple.groblins.common.entity.groblin.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Iterator;

public class ExpandingChunkSearch implements Iterable<BlockPos> {
    private final BlockPos start;
    private final int chunkX;
    private final int chunkZ;
    private final Chunk chunk;

    public ExpandingChunkSearch(BlockPos start, Chunk chunk) {
        this.start = start;
        this.chunk = chunk;
        this.chunkX = chunk.xPosition;
        this.chunkZ = 0;
    }

    public ExpandingChunkSearch(BlockPos start, World world) {
        this(start, world.getChunkFromBlockCoords(start));
    }

    public ExpandingChunkSearch(BlockPos start) {
        this.start = start;
        this.chunkX = start.getX() - (Math.abs(start.getX()) % 16);
        this.chunkZ = start.getZ() - (Math.abs(start.getZ()) % 16);
        this.chunk = null;
    }

    @Override
    public Iterator<BlockPos> iterator() {
        return new ExpandingChunkSearchIterator();
    }

    public int getHeightValue(int x, int z) {
        int chunkX = (((start.getX() + x) % 16) + 16) % 16;
        int chunkZ = (((start.getZ() + z) % 16) + 16) % 16;
        if (chunk != null) return chunk.getHeightValue(chunkX, chunkZ);
        return 0;
    }

    class ExpandingChunkSearchIterator implements Iterator<BlockPos> {
        private int blocksCounted = 0;
        private int radius = 0, radiusIndex, radiusTotal;
        private Pos pos = new Pos(0, 0);
        private Vec vec = new Vec(0, 0);

        @Override
        public boolean hasNext() {
            return blocksCounted < 256;
        }

        @Override
        public BlockPos next() {
            if (blocksCounted >= 256) {
                return null;
            }

            if (radius == 0) {
                blocksCounted++;
                radius = 1;
                radiusIndex = 0;
                radiusTotal = 8;
                pos = new Pos(radius, radius);
                vec = new Vec(-1, 0);
                return start;
            }

            Pos currentPos = new Pos();
            do {
                if(radius > 16) {
                    return null;
                }
                currentPos.copy(pos);

                pos.addVec(vec);
                radiusIndex++;

                if (radiusIndex >= radiusTotal) {
                    radius++;
                    radiusIndex = 0;
                    radiusTotal = radius * 8;
                    pos = new Pos(radius, radius);
                    vec = new Vec(-1, 0);
                } else if (radiusIndex % (radius * 2) == 0) {
                    vec.rotate();
                }

            } while (isOutsideChunk(currentPos.toBlockPos(start, 0)));

            blocksCounted++;
            int y = getHeightValue(currentPos.x, currentPos.z);
            BlockPos res = currentPos.toBlockPos(start, y);
            System.out.println("res" + res);
            return res;
        }

        private boolean isOutsideChunk(BlockPos pos) {
            // System.out.println("isOutsideChunk" + pos);
            return pos.getX() < chunkX || pos.getX() > chunkX+15 || pos.getZ() < chunkZ || pos.getZ() > chunkZ+15;
        }

        private class Pos {
            int x, z;

            Pos(int x, int z) {
                this.x = x;
                this.z = z;
            }

            Pos() {
                this.x = 0;
                this.z = 0;
            }

            BlockPos toBlockPos(BlockPos start, int y) {
                return new BlockPos(start.getX() + x, y, start.getZ() + z);
            }

            void copy(Pos pos) {
                this.x = pos.x;
                this.z = pos.z;
            }

            void addVec(Vec vec) {
                x += vec.x;
                z += vec.z;
            }

            @Override
            public String toString() {
                return "x=" + x + ",z=" + z;
            }
        }

        private class Vec {
            int x, z;

            Vec(int x, int z) {
                this.x = x;
                this.z = z;
            }

            void rotate() {
                int t = x;
                x = -z;
                z = t;
            }
        }
    }
}