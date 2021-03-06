package app;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ActorCtrl extends DBConn{
    public List<String> fetchRoles(String actorName){
        List<String> roleNames = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();
            String query = "select RolleIFilm.Rollenavn from (RolleIFilm natural join FilmPerson) where FilmPerson.Navn='" + actorName + "';";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                roleNames.add(result.getString("Rollenavn"));
            }
        }catch(Exception e){
            System.out.println("Database error when selecting for role names:\n" + e);
        }
        return roleNames;
    }
    public List<String> fetchMovies(String actorName){
        List<String> movieTitles = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();
            String query = "select Film.Tittel from (Film natural join (RolleIFilm natural join FilmPerson)) where (FilmPerson.Navn='" + actorName + "')";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                movieTitles.add(result.getString("Tittel"));
            }
        }catch(Exception e){
            System.out.println("Database error when selecting for movie titles:\n" + e);
        }
        return movieTitles;
    }
    public List<FilmPerson> fetchFilmPersons(){
        List<FilmPerson> filmPersons = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();
            String query = "select * from FilmPerson";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                filmPersons.add(new FilmPerson(Integer.parseInt(result.getString("PID")), result.getString("Navn"), Integer.parseInt(result.getString("Fodselsaar")), result.getString("Fodselsland"), "1".equalsIgnoreCase(result.getString("Regissor")), "1".equalsIgnoreCase(result.getString("Skuespiller")), "1".equalsIgnoreCase(result.getString("Manusforfatter"))));
            }
        }catch(Exception e){
            System.out.println("Database error when selecting for all actors:\n" + e);
        }
        return filmPersons;
    }
}