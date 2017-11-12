package com.tc2r.tc2r.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public class MyContactListener implements ContactListener {

	private int numFootCount;
	private Array<Body> bodiesToRemove;

	public MyContactListener() {
		super();
		bodiesToRemove = new Array<Body>();

	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

		switch(cDef) {
			case B2DVars.BIT_PLAYER | B2DVars.BIT_RED:
				if (fa.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fa.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				if (fb.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fb.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				break;
			case B2DVars.BIT_PLAYER | B2DVars.BIT_GREEN:
				if (fa.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fa.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				if (fb.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fb.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				break;
			case B2DVars.BIT_PLAYER | B2DVars.BIT_BLUE:
				if (fa.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fa.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				if (fb.getFilterData().categoryBits == B2DVars.BIT_PLAYER) {
					if(fb.getUserData().equals("foot")) {
						numFootCount++;
					}
				}
				break;
			case B2DVars.BIT_PLAYER | B2DVars.BIT_CRYSTAL:
				if (fa.getFilterData().categoryBits == B2DVars.BIT_CRYSTAL) {
					// Remove Crystal from fa
					bodiesToRemove.add(fa.getBody());

				} else {
					// Remove Crystal from fb
					bodiesToRemove.add(fb.getBody());
				}

		}

	}

	public Array<Body> getBodiesToRemove() {
		return bodiesToRemove;
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

		if (fa.getUserData() != null && fa.getUserData().equals("foot")){
			numFootCount--;

		}
		if (fb.getUserData() != null && fb.getUserData().equals("foot")){
			numFootCount--;

		}

	}

	public boolean isPlayerOnGround() {
		return (numFootCount > 0);
	}



	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}
