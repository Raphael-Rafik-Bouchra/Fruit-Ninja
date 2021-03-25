package model.gameobjects;

import model.gameobjects.GameObject;
import model.gameobjects.Blade;


public class Field {

	private GameObject object;
	private Blade blade;
	
	public Field() {
		this.blade = new Blade();
	}
	public void setObject(GameObject object) {
		this.object = object;
	}
	public GameObject getObject() {
		return this.object;
	}
	public Blade getBlade() {
		return this.blade;
	}
	
}
