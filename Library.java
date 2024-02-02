

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
		podcasts	= new ArrayList<Podcast>(); 
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{	
		if (content.getType() == Song.TYPENAME)
		{
			// to use the "add()" method of songs, convert content to Song type
			Song song;
			song = (Song) content;
			
			// getErrorMessage and return false if element already exists in the arraylist
			for (Song  element: songs) 
			{
				if (element.equals(song))
				{
					throw new ContentAlreadyExistsException("SONG " + song.getTitle() + " already downloaded");
				}
			}
			// if the condition in the for-each loop is never reached, add the element to the arraylist
			songs.add(song);
			System.out.println("SONG " + song.getTitle() + " Added to Library");
		}

		else if (content.getType() == AudioBook.TYPENAME)
		{
			// to use the "add()" method of audiobooks, convert content to AudioBook type
			AudioBook audiobook = (AudioBook) content;

			for (AudioBook element: audiobooks) 
			{
				if (element.equals(audiobook))
				{
					throw new ContentAlreadyExistsException("AUDIOBOOK " + audiobook.getTitle() + " already downloaded");
				}
			}

			audiobooks.add(audiobook);
			System.out.println("AUDIOBOOK " + audiobook.getTitle() + " Added to Library");
		}

		else if (content.getType() == Podcast.TYPENAME)
		{
			// to use the "add()" method of podcass, convert content to Podcast type
			Podcast podcast = (Podcast) content;

			// getErrorMessage and return false if element already exists in the arraylist
			for (Podcast element: podcasts) 
			{
				if (element.equals(podcast))
				{
					throw new ContentAlreadyExistsException("PODCAST " + podcast.getTitle() + " already downloaded");
				}
			}

			podcasts.add(podcast);
			System.out.println("PODCAST " + podcast.getTitle() + " Added to Library");
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();

		// Going through the songs arraylist and addding artist names to "artists"
		// if it doesn't already contain the artist
		for (Song currSong: songs)
		{
			if (!artists.contains(currSong.getArtist()))
			{
				artists.add(currSong.getArtist());
			}
		}
		
		// Displaying the artists
		for (int i=0; i<artists.size(); i++)
		{
			int index = i+1;
			System.out.println(index + ". " + artists.get(i));
		}
		System.out.println();
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{	
		if (index >= 1 && index <= songs.size())
		{
			Song song = songs.get(index - 1);
			songs.remove(index - 1);
			
			// going through all playlists and deleting song
			for (Playlist playlist : playlists)
			{
				ArrayList<AudioContent> content = playlist.getContent();
				// checking for songs in each playlist
				for (int i = 0; i < content.size(); i++)
				{
					if (content.get(i).getType() == "SONG")
					{
						if (song.equals(content.get(i)))
						{
							playlist.deleteContent(i + 1);
						}
					}
				}
			}
			System.out.println("SONG " + song.getTitle() + " Deleted");
		}

		else
		{
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getYear() == b.getYear())
			{
				return 0;
			}

			else if (a.getYear() > b.getYear())
			{
				return 1;
			}

			else
			{
				return -1;
			}
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getLength() > b.getLength()) return 1;
			else if (a.getLength() < b.getLength()) return -1;
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code

		Collections.sort(songs);
	}
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new IndexOutOfBoundsException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode) 
	{
		if (index < 1 || index > podcasts.size())
		{
			throw new IndexOutOfBoundsException("Podcast not found");
		}
		Podcast currPodcast = podcasts.get(index-1);

		try
		{
		currPodcast.selectSeason(season);
		currPodcast.selectEpisode(episode);
		currPodcast.play();
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season)
	{
		if (index < 1 || index > podcasts.size())
		{
			throw new IndexOutOfBoundsException("Podcast not found");
		}
		Podcast currPodcast = podcasts.get(index-1);
		currPodcast.printTOC(season);
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{

		if (index < 1 || index > audiobooks.size())
		{
			throw new IndexOutOfBoundsException("AudioBook not found");
		}
		AudioBook currAudioBook = audiobooks.get(index-1);
		try{
			currAudioBook.selectChapter(chapter);
			currAudioBook.play();
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())
		{
			throw new IndexOutOfBoundsException("AudioBook not found");
		}
		AudioBook currAudioBook = audiobooks.get(index-1);
		currAudioBook.printTOC();
	}
	
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);
		if (playlists.contains(newPlaylist))
		{
			throw new ContentAlreadyExistsException("Playlist " + title + " Already Exists");
		}
		playlists.add(newPlaylist);
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		if (!playlists.contains(new Playlist(title)))
		{
			throw new ContentDoesNotExistException("Playlist Does Not Exist");
		}

		for (Playlist playlist : playlists)
		{
			//locate the playlist in playlists
			if (title.equals(playlist.getTitle()))
			{
				playlist.printContents();
			}
		}
				
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{

		if (!playlists.contains(new Playlist(playlistTitle)))
		{
			throw new ContentDoesNotExistException("Playlist Does Not Exist");
		}

		for (Playlist playlist : playlists)
		{
			//locate the playlist in playlists
			if (playlistTitle.equals(playlist.getTitle()))
			{
				playlist.playAll();
			}
		}
		
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		if (!playlists.contains(new Playlist(playlistTitle)))
		{
			throw new ContentDoesNotExistException("Playlist Does Not Exist");
		}


		for (Playlist playlist : playlists)
		{
			//locate the playlist in playlists
			if (playlistTitle.equals(playlist.getTitle()))
			{
				if (indexInPL < 1 || indexInPL > playlist.getPlaylistSize())
				{
					throw new IndexOutOfBoundsException("Invalid Content#");
				}
		
				playlist.play(indexInPL);
			}
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		if (!playlists.contains(new Playlist(playlistTitle)))
		{
			throw new ContentDoesNotExistException("Unable to find the required content");
		}

		else if (!type.equalsIgnoreCase("SONG") 
				&& !type.equalsIgnoreCase("AUDIOBOOOK") 
				&& !type.equalsIgnoreCase("PODCAST"))
		{
			throw new ContentDoesNotExistException("Invalid Content Type");
		}

		// locate the required playlist from the playlists arraylist
		for (Playlist playlist : playlists)
		{
			if (playlistTitle.equals(playlist.getTitle()))
			{
				// once we have the required playlist, sort between 
				// different audiocontent and add the appropriate content to the playlist
				try{
					if (type.equalsIgnoreCase(Song.TYPENAME))
					{
						playlist.addContent(songs.get(index - 1));
					}

					else if (type.equalsIgnoreCase(AudioBook.TYPENAME))
					{
						playlist.addContent(audiobooks.get(index - 1));
					}
				}
				catch(IndexOutOfBoundsException e)
				{
					System.out.println(e.getMessage());
				}
			}
		} 
		
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		if (!playlists.contains(new Playlist(title)))
		{
			throw new ContentDoesNotExistException("Playlist Does Not Exist");
		}

		// locate the required playlist from the playlists arraylist
		for (Playlist playlist : playlists)
		{
			if (title.equals(playlist.getTitle()))
			{
				try{
				playlist.deleteContent(index);}
				catch (IndexOutOfBoundsException e)
				{System.out.println(e.getMessage());}
			}
		}
	}
	
}

// Creating custom exceptions
class ContentAlreadyExistsException extends RuntimeException
{ 
	public ContentAlreadyExistsException() {}
	public ContentAlreadyExistsException(String message)
	{ 
		super(message);
	}
}

class ContentDoesNotExistException extends RuntimeException
{ 
	public ContentDoesNotExistException() {}
	public ContentDoesNotExistException(String message)
	{ 
		super(message);
	}
}
