import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        ArrayList<String> castMembers = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++)
        {
            String[] castList = movies.get(i).getCast().split("\\|");

            for (String castMember : castList)
            {
                castMember = castMember.trim();

                if (!castMembers.contains(castMember))
                {
                    castMembers.add(castMember);
                }
            }
        }

        ArrayList<String> results = new ArrayList<String>();
        for (String castMember : castMembers)
        {
            if (castMember.toLowerCase().contains(searchTerm))
            {
                results.add(castMember);
            }
        }

        Collections.sort(results);

        for (int i = 0; i < results.size(); i++)
        {
            String castMember = results.get(i);
            System.out.println((i + 1) + ". " + castMember);

            ArrayList<String> castMovies = new ArrayList<String>();
            for (int j = 0; j < movies.size(); j++)
            {
                String[] castList = movies.get(j).getCast().split("\\|");
                if (Arrays.asList(castList).contains(castMember))
                {
                    castMovies.add(movies.get(j).getTitle());
                }
            }
            Collections.sort(castMovies);
            for (int j = 0; j < castMovies.size(); j++)
            {
                String movieTitle = castMovies.get(j);
                System.out.println("\t" + (j + 1) + ". " + movieTitle);
            }
        }
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++)
        {
            String movieKey = movies.get(i).getKeywords();
            movieKey = movieKey.toLowerCase();

            if (movieKey.indexOf(searchTerm) != -1)
            {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {

        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            String[] movieGenres = movies.get(i).getGenres().split("\\|");
            for (String genre : movieGenres)
            {
                genre = genre.trim();
                if (!genres.contains(genre))
                {
                    genres.add(genre);
                }
            }
        }

        System.out.println("\nList of Genres:");
        for (int i = 0; i < genres.size(); i++)
        {
            System.out.println("- " + genres.get(i));
        }

        System.out.print("\nEnter a genre to see all movies of that genre: ");
        String selectedGenre = scanner.nextLine().toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String[] movieGenres = movies.get(i).getGenres().split("\\|");
            for (String genre : movieGenres)
            {
                genre = genre.trim();
                if (genre.equalsIgnoreCase(selectedGenre))
                {
                    results.add(movies.get(i));
                    break;
                }
            }
        }

        sortResults(results);

        System.out.println("\n" + results.size() + " movies found in the " + selectedGenre + " genre:");
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> sortedMovies = new ArrayList<Movie>(movies);

        Collections.sort(sortedMovies, new Comparator<Movie>() {
            public int compare(Movie m1, Movie m2) {
                if (m1.getUserRating() < m2.getUserRating()) {
                    return 1;
                } else if (m1.getUserRating() > m2.getUserRating()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        System.out.println("Top 50 highest rated movies:");
        for (int i = 0; i < 50 && i < sortedMovies.size(); i++)
        {
            String title = sortedMovies.get(i).getTitle();
            double rating = sortedMovies.get(i).getUserRating();
            System.out.printf("%d. %s (%.1f)\n", i+1, title, rating);
        }

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRevenue()
    {
        ArrayList<Movie> moviesCopy = new ArrayList<Movie>(movies);

        Collections.sort(moviesCopy, new Comparator<Movie>()
        {
            public int compare(Movie m1, Movie m2)
            {
                return m2.getRevenue() - m1.getRevenue();
            }
        });

        System.out.println("Top 50 movies by revenue:");
        for (int i = 0; i < 50 && i < moviesCopy.size(); i++)
        {
            System.out.println((i+1) + ". " + moviesCopy.get(i).getTitle() + " - " + moviesCopy.get(i).getRevenue());
        }

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}