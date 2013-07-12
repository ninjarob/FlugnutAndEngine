package com.pkp.flugnut.FlugnutAndEngine.Service;

public abstract class BuildingPiece {
//    public Body clickBody;
//    public BodyDef clickBodyDef;
//    public FlugnutWorld flugnutWorld;
//    public boolean done = false;
//    public boolean clicked = false;
//    public boolean clickable = false;
//    public boolean pickupable = false;
//    protected Boolean pickedUp = false;
//    public boolean flugnutPresent = false;
//    public int weight = 1;
//
//    public BuildingPiece(GLGame game, float startx, float starty, float width, float height, String filename, boolean halfOffset) {
//        super(game, filename, new Vec2(startx, starty), width, height);
//        this.pickedUp = false;
//    }
//
//	public BuildingPiece(GLGame game, float startx, float starty, float width, float height, String filename) {
//		super(game, filename, new Vec2(startx, starty), width, height, -width/2, -height/2);
//        this.pickedUp = false;
//	}
//
//    public BuildingPiece(GLGame game, float startx, float starty, float width, float height, String filename, float tcstartx, float tcstarty, float tcendx, float tcendy) {
//        super(game, filename, new Vec2(startx, starty), width, height, -width/2, -height/2, tcstartx, tcstarty, tcendx, tcendy);
//        this.pickedUp = false;
//    }
//
//    public abstract void initPhysicsWorld();
//
//    public abstract void update(float deltaTime);
//
//    public abstract void click(boolean click);
//
//	@Override
//	public void draw(GL10 gl) {
//        gl.glLoadIdentity();
//        gl.glTranslatef(body.getPosition().x*Constants.V_SCALE, body.getPosition().y*Constants.V_SCALE, 0);
//        gl.glRotatef(body.getAngle()*Utilities.TO_DEGREES, 0, 0, 1);
//        image.draw(gl);
//	}
//
//    public void destroy() {
//        FlugnutWorld.bodiesToDestroy.add(body);
//        super.destroy();
//    }
//
//    public Boolean getPickedUp() {
//        return pickedUp;
//    }
//
//    public void setPickedUp(Boolean pickedUp) {
//        this.pickedUp = pickedUp;
//    }
//
//    protected void initClickBody(Vec2[] verts) {
//        destroyClickbody();
//        clickBodyDef = new BodyDef();
//        clickBodyDef.type = BodyType.STATIC;
//        clickBodyDef.position = new Vec2(body.getPosition());
//        clickBodyDef.position.x = def.position.x*Constants.V_SCALE;
//        clickBodyDef.position.y = def.position.y*Constants.V_SCALE;
//        clickBody = flugnutWorld.world.createBody(clickBodyDef);
//        PolygonShape j = new PolygonShape();
//        j.set(verts, 4);
//        FixtureDef fd = new FixtureDef();
//        fd.shape = j;
//        fd.filter.categoryBits = Constants.NON_COL_CATEGORY;
//        fd.filter.maskBits = Constants.NON_COL_MASK;
//        clickBody.createFixture(fd);
//        clickBody.setUserData(this);
//    }
//
//    protected void destroyClickbody() {
//        if (clickBody != null) {
//            FlugnutWorld.bodiesToDestroy.add(clickBody);
//            clickBody = null;
//        }
//    }
}