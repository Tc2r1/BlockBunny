package com.tc2r.tc2r.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description:
 */
public class MyContactListener implements ContactListener {

	private int numFootCount;


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
		}

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
