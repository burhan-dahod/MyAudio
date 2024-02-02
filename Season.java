

import java.util.ArrayList;

/**
 * Season
 */
public class Season 
{

    private ArrayList<String> episodeTitles;
    private ArrayList<String> episodeFiles;
    private ArrayList<Integer> episodeLength;

    public Season()
    {
        this.episodeTitles = new ArrayList<String>();
        this.episodeFiles = new ArrayList<String>();
        this.episodeLength = new ArrayList<Integer>();
    }
    public void addEpisodeTitle(String episodeName)
    {
        episodeTitles.add(episodeName);
    }

    public void addEpisodeFile(String episodeContent)
    {
        episodeFiles.add(episodeContent);
    }

    public void addEpisodeLength(int value)
    {
        episodeLength.add(value);
    }

    public ArrayList<String> getEpisodeTitles()
    {
        return episodeTitles;
    }

    public ArrayList<String> getEpisodeFiles()
    {
        return episodeFiles;
    }

    // get a specific episode from the arraylist episodeFiles
    public String getEpisode(int episode)
    {
        return getEpisodeFiles().get(episode);
    }

    // get a specific episode title from the arraylist episodeTitles
    public String getEpisodeTitle(int episode)
    {
        return getEpisodeTitles().get(episode);
    }
}
