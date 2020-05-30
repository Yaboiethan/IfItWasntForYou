/*
Offset colliders function the same as normal colliders, however, their bounds don't take up the entire object.
Most scene objects will be OffsetColliders since the player on the ground should'nt collide with the leaves on tall trees.
 */
import java.awt.*;

public class OffsetCollider extends Collider
{
    private Position offsetPos; //To be added onto GameObject position
    private int widthOffset;
    private int heightOffset;

    public OffsetCollider(GameObject g) //Blank
    {
        super(g);
        offsetPos = new Position();
        widthOffset = 0;
        heightOffset = 0;
    }

    public OffsetCollider(GameObject g, Position offset, Dimension boxBounds) //Fill
    {
        super(g);
        offsetPos = offset;
        widthOffset = boxBounds.width;
        heightOffset = boxBounds.height;
    }

    //Sets and gets
    public Position getOffsetPos() {
        return offsetPos;
    }

    public void setOffsetPos(Position offsetPos) {
        this.offsetPos = offsetPos;
    }

    public int getWidthOffset() {
        return widthOffset;
    }

    public void setWidthOffset(int widthOffset) {
        this.widthOffset = widthOffset;
    }

    public int getHeightOffset() {
        return heightOffset;
    }

    public void setHeightOffset(int heightOffset) {
        this.heightOffset = heightOffset;
    }

    @Override
    protected void setCollider()
    {
        if(offsetPos == null) //Not yet
        {
            return;
        }
        col.setRect(thisObj.myPos.x + offsetPos.x - 1, thisObj.myPos.y + offsetPos.y - 1,
                thisObj.getSpriteSize().width + widthOffset + 1, thisObj.getSpriteSize().height + heightOffset + 1);
    }
}
