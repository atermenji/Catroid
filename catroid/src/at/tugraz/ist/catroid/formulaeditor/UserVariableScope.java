package at.tugraz.ist.catroid.formulaeditor;

import at.tugraz.ist.catroid.ProjectManager;

public class UserVariableScope {
	private String name;
	private ScopeType scopeType;

	public static enum ScopeType {
		SPRITE, PROJECT, UNIVERSE
	}

	public UserVariableScope(String name, ScopeType scopeType) {
		this.scopeType = scopeType;
		this.name = name;
	}

	public boolean checkScope() {

		switch (scopeType) {
			case SPRITE:
				if (ProjectManager.getInstance().getCurrentSprite().getName().equals(name)) {
					return true;
				}
				break;
			case PROJECT:
				if (ProjectManager.getInstance().getCurrentProject().getName().equals(name)) {
					return true;
				}
				break;
		}

		return false;

	}
}
