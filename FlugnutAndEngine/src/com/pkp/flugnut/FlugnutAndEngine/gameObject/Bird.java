package com.pkp.flugnut.FlugnutAndEngine.gameObject;


public class Bird {
//    public PolygonShape ps;
//    public float startx;
//    public float timeSpent = 0;
//    public boolean interact = false;
//    public BlockBuilding building;
//    public BirdPath bp;
//    public boolean caged = false;
//    public String name;
//    private Animation animationRight;
//    private Animation animationLeft;
//    private Animation animationClickedRight;
//    private Animation animationClickedLeft;
//
//    public Bird(GLGame game, float startx, float starty, FlugnutWorld flugnutWorld, BlockBuilding building) {
//        super(game, startx, starty,
//                Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT,
//                Constants.BIRD_PATH, 0f, .2f, .25f, .3f);
//        this.flugnutWorld = flugnutWorld;
//        initPhysicsWorld();
//        doInitClickBody();
//        bp = new BirdPath(this);
//        this.building = building;
//        initAnimations();
//    }
//
//    private void initAnimations() {
//        List spriteSequence = new ArrayList<Vec2>();
//        spriteSequence.add(new Vec2(0, 2));
//        spriteSequence.add(new Vec2(1, 2));
//        spriteSequence.add(new Vec2(2, 2));
//        spriteSequence.add(new Vec2(3, 2));
//        spriteSequence.add(new Vec2(2, 2));
//        spriteSequence.add(new Vec2(1, 2));
//        spriteSequence.add(new Vec2(0, 2));
//        animationRight = new Animation(this, Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, spriteSequence, 0.25f, 0.1f, 2);
//        spriteSequence = new ArrayList<Vec2>();
//        spriteSequence.add(new Vec2(0, 3));
//        spriteSequence.add(new Vec2(1, 3));
//        spriteSequence.add(new Vec2(2, 3));
//        spriteSequence.add(new Vec2(3, 3));
//        spriteSequence.add(new Vec2(2, 3));
//        spriteSequence.add(new Vec2(1, 3));
//        spriteSequence.add(new Vec2(0, 3));
//        animationLeft = new Animation(this, Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, spriteSequence, 0.25f, 0.1f, 2);
//        spriteSequence = new ArrayList<Vec2>();
//        spriteSequence.add(new Vec2(0, 7));
//        spriteSequence.add(new Vec2(1, 7));
//        spriteSequence.add(new Vec2(2, 7));
//        spriteSequence.add(new Vec2(3, 7));
//        spriteSequence.add(new Vec2(2, 7));
//        spriteSequence.add(new Vec2(1, 7));
//        spriteSequence.add(new Vec2(0, 7));
//        animationClickedRight = new Animation(this, Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, spriteSequence, 0.25f, 0.1f, 2);
//        spriteSequence = new ArrayList<Vec2>();
//        spriteSequence.add(new Vec2(0, 8));
//        spriteSequence.add(new Vec2(1, 8));
//        spriteSequence.add(new Vec2(2, 8));
//        spriteSequence.add(new Vec2(3, 8));
//        spriteSequence.add(new Vec2(2, 8));
//        spriteSequence.add(new Vec2(1, 8));
//        spriteSequence.add(new Vec2(0, 8));
//        animationClickedLeft = new Animation(this, Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, spriteSequence, 0.25f, 0.1f, 2);
//
//    }
//
//    public Vec2[] getVerts() {
//        Vec2[] verts = new Vec2[4];
//        if (!caged) {
//            verts[0] = new Vec2(-10.5f, 10.5f);
//            verts[1] = new Vec2(10.5f, 10.5f);
//            verts[2] = new Vec2(10.5f, -10.5f);
//            verts[3] = new Vec2(-10.5f, -10.5f);
//        }
//        if (caged) {
//            verts[0] = new Vec2(-10.5f, -10.5f);
//            verts[1] = new Vec2(10.5f, -10.5f);
//            verts[2] = new Vec2(10.5f, 10.5f);
//            verts[3] = new Vec2(-10.5f, 10.5f);
//        }
//        return verts;
//    }
//
//    @Override
//    public void initPhysicsWorld() {
//        def.type = BodyType.DYNAMIC;
//        body = flugnutWorld.world.createBody(def);
//        ps = new PolygonShape();
//        Vec2[] verts = getVerts();
//        ps.set(verts, 4);
//        FixtureDef fd = new FixtureDef();
//        fd.shape = ps;
//        fd.density = 12f;
//        fd.friction = .0f;
//        fd.restitution = 1.2f;
//        fd.filter.categoryBits = Constants.MISC_CATEGORY;
//        if (!caged) {
//            fd.filter.maskBits = Constants.MISC_MASK;
//        }
//        else {
//            fd.filter.maskBits = Constants.MISC_MASK_COLLIDE;
//        }
//        body.createFixture(fd);
//        body.setUserData(this);
//        body.setGravityScale(0);
//        body.setLinearVelocity(new Vec2(100, -100));
//    }
//
//    private void initPhysicsForCaptureBird() {
//        def.type = BodyType.STATIC;
//        body = flugnutWorld.world.createBody(def);
//        ps = new PolygonShape();
//        Vec2[] verts = getVerts();
//        ps.set(verts, 4);
//        FixtureDef fd = new FixtureDef();
//        fd.shape = ps;
//        fd.density = .1f;
//        fd.friction = .1f;
//        fd.filter.categoryBits = Constants.MISC_CATEGORY;
//        fd.filter.maskBits = Constants.MISC_MASK;
//        body.createFixture(fd);
//        body.setUserData(this);
//        body.setGravityScale(0);
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        if (!getPickedUp()) {
//            if (body.m_type == BodyType.STATIC) {
//                FlugnutWorld.bodiesToDestroy.add(body);
//                initPhysicsWorld();
//            }
//            if (!caged) {
//                bp.haveFun(50, IScreen.glGraphics.getWidth()-50);
//            }
//            else {
//                //bp.haveFun((building.body.getPosition().x - building.width/2) + 30, (building.body.getPosition().x + building.width/2) - 30);
//            }
//            if (body.getLinearVelocity().x < 0) {
//                if (!clicked) {
//                    animationLeft.animationStep(deltaTime);
//                }
//                else {
//                    animationClickedLeft.animationStep(deltaTime);
//                }
//            }
//            else {
//                if (!clicked) {
//                    animationRight.animationStep(deltaTime);
//                }
//                else {
//                    animationClickedRight.animationStep(deltaTime);
//                }
//            }
//            if (!caged) {
//                doInitClickBody();
//            }
//        }
//        if (!caged && !getPickedUp() && flugnutPresent && clicked) {
//            setPickedUp(true);
//            click(false);
//            flugnutPresent = false;
//            flugnutWorld.guy.setObjectClicked(null);
//            if (flugnutWorld.guy.objectCarried != null) {
//                flugnutWorld.guy.objectCarried.setPickedUp(false);
//                flugnutWorld.guy.objectCarried.initPhysicsWorld();
//            }
//            flugnutWorld.guy.objectCarried = this;
//        }
//        if (!caged && getPickedUp()) {
//            FlugnutWorld.bodiesToDestroy.add(body);
//            destroyClickbody();
//            def.position.set(flugnutWorld.guy.body.getPosition().x, flugnutWorld.guy.body.getPosition().y);
//            initPhysicsForCaptureBird();
//            if (null != flugnutWorld.guy.getObjectClicked() && flugnutWorld.guy.getObjectClicked().filename.equals(this.name))
//                flugnutWorld.guy.setObjectClicked(null);
//        }
//        float speed = (float)Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + Math.pow(body.getLinearVelocity().y, 2));
//        if (speed > 70) {
//            float ratio = 70/speed;
//            body.setLinearVelocity(new Vec2(body.getLinearVelocity().x*ratio,body.getLinearVelocity().y*ratio));
//        }
//
//    }
//
//
//    //TODO both clicked and nonclicked versions of the images need to be on the same sprite.
//    @Override
//    public void click(boolean click) {
//        if (!caged) {
//            clicked = click;
//        }
//    }
//
//    private void doInitClickBody() {
//        Vec2[] verts = new Vec2[4];
//        verts[0] = new Vec2(-12.5f, 12.5f);
//        verts[1] = new Vec2(12.5f, 12.5f);
//        verts[2] = new Vec2(12.5f, -12.5f);
//        verts[3] = new Vec2(-12.5f, -12.5f);
//        initClickBody(verts);
//    }
//
//    @Override
//    public void draw(GL10 gl) {
//        super.draw(gl);
//    }
}