package Engine;

//Physics things
public class Position
{
    int x;
    int y;
    public final static Position ORIGIN = new Position(0,0);
    public enum Direction {NORTH, EAST, WEST, SOUTH}

    public Position()
    {
        x = 0;
        y = 0;
    }

    public Position(int sx, int sy)
    {
        x = sx;
        y = sy;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int sx)
    {
        x = sx;
    }

    public void setY(int sy)
    {
        y = sy;
    }

    public String toString()
    {
        return "( " + x + ", " + y + " )";
    }

    public boolean equals(Position other)
    {
        boolean ret = false;
        if(x == other.x && y == other.y)
        {
            ret = true;
        }
        return ret;
    }

    public static Direction getOppositeDirection(Direction dir)
    {
        switch (dir)
        {
            case NORTH:
                return Direction.SOUTH;

            case SOUTH:
                return Direction.NORTH;

            case EAST:
                return Direction.WEST;

            case WEST:
                return Direction.EAST;
        }
        return null; //Something went wrong
    }

    public static Direction getOppositeDirection(char dir)
    {
        switch (dir)
        {
            case 'S':
                return Direction.SOUTH;

            case 'W':
                return Direction.NORTH;

            case 'A':
                return Direction.WEST;

            case 'D':
                return Direction.EAST;
        }
        return null; //Something went wrong
    }

    public static char getDirectionAbbrev(Direction dir)
    {
        switch (dir)
        {
            case NORTH:
                return 'N';

            case SOUTH:
                return 'S';

            case EAST:
                return 'E';

            case WEST:
                return 'W';
        }
        return '\u0000'; //Something went wrong
    }

    public static Direction charToDirection(char c)
    {
        c = Character.toUpperCase(c);
        switch (c)
        {
            case 'W': //North
                return Direction.NORTH;

            case 'A': //West
                return Direction.WEST;

            case 'S': //South
                return Direction.SOUTH;

            case 'D': //East
                return Direction.EAST;
        }
        return null;
    }
}
