package game.backend.cell;

// This class generates a fixed amount of a determined element with a certain frequency, or a Candy otherwise

import game.backend.Grid;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import java.lang.reflect.InvocationTargetException;

public abstract class SpecialCandyGeneratorCell extends GeneratorCell{

    private Class<Candy> specialCandy;
    private int specialCandiesToPlace;
    private double specialCandyFrequency;

    public SpecialCandyGeneratorCell(Grid grid, Class specialCandyClass, int specialCandiesAmount, double specialCandyFrequency) {
        super(grid);
        this.specialCandy = specialCandyClass;
        this.specialCandiesToPlace = specialCandiesAmount;
        this.specialCandyFrequency = specialCandyFrequency;
    }

    @Override
    public Element getContent() {
        int i = (int)(Math.random() * CandyColor.values().length);
        try {
            if(specialCandiesToPlace>0 && Math.random()<specialCandyFrequency){
                specialCandiesToPlace -= 1;
                return specialCandy.getDeclaredConstructor(Grid.class, CandyColor.class).newInstance(grid, CandyColor.values()[i]);
            }else{
                return new Candy(CandyColor.values()[i]);
            }
        } catch(IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }


}
