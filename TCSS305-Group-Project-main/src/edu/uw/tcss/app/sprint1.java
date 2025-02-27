import java.util.List;

private IndividualPiece[] pieces() {
        return new IndividualPiece[]{
            new IndividualPiece(new Point[]{new Point(9, 17), new Point(7, 16), new Point(8, 16), new Point(9, 16)}, Block.L),
            new IndividualPiece(new Point[]{new Point(5, 17), new Point(6, 17), new Point(5, 16), new Point(6, 16)}, Block.O),
            new IndividualPiece(new Point[]{new Point(2, 16), new Point(3, 16), new Point(3, 15), new Point(4, 15)}, Block.Z),
            new IndividualPiece(new Point[]{new Point(0, 16), new Point(0, 15), new Point(0, 14), new Point(0, 13)}, Block.I),
            new IndividualPiece(new Point[]{new Point(7, 13), new Point(7, 12), new Point(8, 12), new Point(9, 12)}, Block.J),
            new IndividualPiece(new Point[]{new Point(1, 13), new Point(0, 12), new Point(1, 12), new Point(2, 12)}, Block.T),
            new IndividualPiece(new Point[]{new Point(5, 12), new Point(4, 12), new Point(6, 13), new Point(5, 13)}, Block.S)
        };
    }

    private GameControls.FrozenBlocks frozenBlocks() {
        return new GameControls.FrozenBlocks(List.of(
                new Block[]{null, Block.I, Block.I, Block.J, Block.Z, Block.T, Block.T, Block.T, Block.Z, Block.Z},
                new Block[]{null, Block.I, Block.I, Block.J, Block.Z, Block.Z, Block.T, Block.Z, Block.Z, null},
                new Block[]{null, Block.I, Block.I, Block.J, Block.J, Block.Z, Block.L, Block.L, Block.L, Block.L},
                new Block[]{null, Block.I, Block.I, Block.J, Block.J, Block.J, Block.Z, Block.Z, Block.L, Block.L},
                new Block[]{null, Block.T, Block.J, Block.J, Block.J, Block.J, Block.S, Block.S, Block.L, Block.L},
                new Block[]{Block.O, Block.O, null, Block.J, Block.J, Block.J, Block.S, null, Block.L, null},
                new Block[]{Block.O, Block.O, null, null, null, null, null, null, Block.L, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null},
                new Block[]{null, null, null, null, null, null, null, null, null, null}));
    }

    private IndividualPiece nextPiece() {
        return new IndividualPiece(new Point[] {new Point(1, 2),new Point(0, 1),new Point(1, 1),new Point(2, 1)}, Block.T);
    }