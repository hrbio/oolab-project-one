package agh.ics.oop.Animal;

import agh.ics.oop.Genes.CorrectionGene;
import agh.ics.oop.Genes.Gene;
import agh.ics.oop.Genes.RandomGene;
import agh.ics.oop.Maps.WorldMap;
import agh.ics.oop.Utility.Directions;
import agh.ics.oop.Utility.Options;
import agh.ics.oop.Utility.Vector2D;

import java.util.Random;

public abstract class Animal {
    private int energy;
    private Vector2D position;
    private final Gene genes;
    private final WorldMap map;


    protected int curGeneIndex = 0;
    protected final int geneLength;

    private static final Random random = new Random();

    public Animal(Options options, WorldMap map) {
        this.energy = options.initialEnergy;

        this.map = map;
        this.position = new Vector2D(random.nextInt(map.getWidth()), random.nextInt(map.getHeight()));

        this.geneLength = options.geneLength;
        this.genes = switch (options.geneType) {
            case RANDOM -> new RandomGene(geneLength);
            case CORRECTION -> new CorrectionGene(geneLength);
        };
    }

    public Animal(Options options, WorldMap map, Animal parent1, Animal parent2) {
        this.energy = options.energyToBreed*2;
        this.map = map;
        this.position = parent1.getPosition();

        this.geneLength = options.geneLength;

        this.genes = switch (options.geneType) {
            case RANDOM -> new RandomGene(geneLength);
            case CORRECTION -> new CorrectionGene(geneLength);
        };
    }

    protected abstract void nextGene();

    public void move() {
        int curGene = genes.getGene(this.curGeneIndex);
        nextGene();
        this.map.moveAnimal(this, Directions.values()[curGene]);
    }

    public void decrementEnergy() {
        this.energy--;
    }

    public boolean isDead() {
        return this.energy == 0;
    }
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public void eat(int eatenEnergy) {
        this.energy += eatenEnergy;
    }

}
