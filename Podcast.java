
import java.util.ArrayList;

public class Podcast extends AudioContent
{

    public static final String TYPENAME = "PODCAST";
    private String host;
    private ArrayList<Season> seasons;
    private int numOfSeasons;
    private int currSeason = 0;
    private int currEpisode = 0;


    public Podcast(String title, int year, String id, String type, String audioFile, int length, String host,
    ArrayList<Season> seasons, int numOfSeasons)
    {
        super(title, year, id, type, audioFile, length);
        this.host = host;
        this.numOfSeasons = numOfSeasons;
        this.seasons = seasons;
    }

    public String getType()
	{
		return TYPENAME;
	}
    
    // Print information about the podcast. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print host, currSeason
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Host: " + this.host);
		System.out.println("Seasons: " + this.numOfSeasons);
	}

    // Play the podcast by setting the audioFile to the current episode title
	// followed by the current episode (from seasons array list)
	// Then make use of the the play() method of the superclass
	public void play()
	{
        Season season = seasons.get(currSeason);
		super.setAudioFile(season.getEpisodeTitle(currEpisode));
		super.play();
		System.out.println(season.getEpisode(currEpisode));
	}
    
    // Select a specific season to play 
    public void selectSeason(int season)
	{
		if (season >= 1 && season <= seasons.size())
		{
			currSeason = season - 1;
		}

		else{
			throw new IndexOutOfBoundsException("Invalid season");
		}
	}

    // Select a specific episode to play 
    public void selectEpisode(int episode)
	{   
        Season season = seasons.get(currSeason);
		if (episode >= 1 && episode <= season.getEpisodeTitles().size())
		{
			currEpisode = episode - 1;
		}

		else{
			throw new IndexOutOfBoundsException("Invalid episode");
		}
	}

    // Print the episode titles for the given season of the given podcast
	public void printTOC(int seasonNumber)
	{
		if (seasonNumber < 1 || seasonNumber > seasons.size())
		{
			Season season = seasons.get(seasonNumber - 1);
			for (int i=0; i < season.getEpisodeTitles().size(); i++)
			{
				int index = i+1;
				System.out.println("Episode " + index + ". " + season.getEpisodeTitle(i) + "\n");
			}
		}

		else{
			System.out.println("Invalid Chapter Number");
		}
	}

	public String getTitle()
	{
		return super.getTitle();
	}
}
