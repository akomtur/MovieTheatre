package de.kaubisch.movietheatre.model.favorites;

/**
 * Created by kaubisch on 07.02.16.
 */
public class Favorite {
    public int id;
    public String imagePath;

    public Favorite(int id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }
}
