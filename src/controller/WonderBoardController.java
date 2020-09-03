package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.reflections.Reflections;
import model.board.WonderBoard;

public class WonderBoardController {

	private Random rand = new Random();
	private SevenWondersController swController;

	private ArrayList<Class<? extends WonderBoard>> boards = new ArrayList<Class<? extends WonderBoard>>();
	
	public WonderBoardController(SevenWondersController swController) {
		this.swController=swController;
		loadBoardClasses();
	}
	
	public WonderBoard createWonderBoard(String name) {
		return createWonderBoard(getClassByName(name));
	}
	
	public WonderBoard createWonderBoard(Class<? extends WonderBoard> clazz) {
		if(this.boards.isEmpty())loadBoardClasses();
		
		if(this.boards.contains(clazz)) {
			this.boards.remove(clazz);
			try {
				Constructor<? extends WonderBoard> con = clazz.getConstructor();
				return con.newInstance(new Object[]{});
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Class<? extends WonderBoard> getClassByName(String name){
		for(Class<? extends WonderBoard> clazz : this.boards) {
			if(clazz.getName().equalsIgnoreCase(name))return clazz;
		}
		
		return null;
	}
	
	public String[] getWonderBoardNames() {
		String[] names = new String[this.boards.size()];
		
		for(int i = 0; i < this.boards.size(); i++) {
			names[i] = this.boards.get(i).getName();
		}
		return names;
	}
	
	public WonderBoard createWonderBoard() {
		Class<? extends WonderBoard> clazz = this.boards.get( randInt(0,this.boards.size()) );
		return createWonderBoard(clazz);
	}
	
	public int randInt(int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public void reset() {
		this.boards.clear();
	}
	
	private void loadBoardClasses() {
		Reflections reflections = new Reflections( "model.board" );
		List<Class<? extends WonderBoard>> moduleClasses = new ArrayList<>( reflections.getSubTypesOf( WonderBoard.class ) );

		Collections.sort(moduleClasses, new Comparator<Class<? extends WonderBoard>>() {
		    @Override
		    public int compare(Class<? extends WonderBoard> o1, Class<? extends WonderBoard> o2) {
		        return o1.getSimpleName().compareTo(o2.getSimpleName());
		    }
		});
		
		for ( Class<? extends WonderBoard> clazz : moduleClasses ){
			this.boards.add(clazz);
		}
	}
}
