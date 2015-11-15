package map;

import java.util.List;

public class Map {
	
    private List<List<Entity>> map;

    public Map(List<List<Entity>> m){
        this.map = m;
    }

    public List<List<Entity>> getMap() {
        return map;
    }

    public void setMap(List<List<Entity>> map) {
        this.map = map;
    }
}