public class Cell {
    private int x;
    private int y;
    private CellEnum cellType;
    private CellElement cellElement;
    private CellStatus status;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellType = CellEnum.EMPTY;
        this.status = CellStatus.UNVISITED;
        this.cellElement = new Empty();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEnum getType() {
        return cellType;
    }

    public void setType(CellEnum cellType) {
        this.cellType = cellType;

        switch(cellType) {
            case EMPTY:
                this.cellElement = new Empty();
                break;
            case ENEMY:
                this.cellElement = new Enemy();
                break;
            case SHOP:
                this.cellElement = new Shop();
                break;
            case FINISH:
                this.cellElement = new Finish();
                break;
        }

    }

    public CellStatus getStatus() {
        return status;
    }
    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public CellElement getCellElement() {
        return cellElement;
    }

    public String toString() {
        if(status == CellStatus.VISITED)
            return String.valueOf(cellElement.toCharacter());
        return "?";
    }
}
