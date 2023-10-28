// Name - Burhanuddin Dahodwala
// Student Number - 501153209

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.io.File;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents; 
	private Map<String, Integer> titleMap;
	private Map<String, ArrayList<Integer>> artistMap;
	private Map<String, ArrayList<Integer>> genreMap;
	
	public AudioContentStore()
	{
		try
		{
		contents = constructContents();
		titleMap = setTitleMap();
		artistMap = setArtistMap();
		genreMap = setGenreMap();
		}

		catch (IOException e)
		{
			System.out.println("IOError : " + e.getMessage());
			System.exit(1);
		}
	}
	
	
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}
	
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}


	// If the audio content with titleName is found in the store then print the 
	// index of this content and the info for this content
	public void searchTitle(String titleName)
	{
		if (titleMap.containsKey(titleName))
		{
			int i = titleMap.get(titleName);
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}

		else
		{
			System.out.println("No matches for " + titleName);
		}
	}
	// If the audio content with this artist name is found in the store then 
	// print the indices and info of all audio content with this artist
	public void searchArtist(String artistName)
	{
		if (artistMap.containsKey(artistName))
		{
			ArrayList<Integer> values = artistMap.get(artistName);
			for (int i : values)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		else
		{
			System.out.println("No matches for " + artistName);
		}
	}

	// If the song with this genre is found in the store then 
	// print the indices and info of all songs with this genre
	public void searchGenre(String genre)
	{
		if (genreMap.containsKey(genre))
		{
			ArrayList<Integer> values = genreMap.get(genre);
			for (int i : values)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		else
		{
			System.out.println("No matches for " + genre);
		}
	}

	public ArrayList<Integer> getArtistIndices(String artist)
	{
		if (artistMap.containsKey(artist))
		{
			return artistMap.get(artist);
		}

		else
		{
			throw new ContentDoesNotExistException("Artist Not found");
		}
	}


	public ArrayList<Integer> getGenreIndices(String genre)
	{
		if (genreMap.containsKey(genre))
		{
			return genreMap.get(genre);
		}

		else
		{
			throw new ContentDoesNotExistException("Invalid Genre");
		}
	}

	private ArrayList<AudioContent> constructContents() throws IOException
	{
		ArrayList<AudioContent> result = new ArrayList<>(); 

		// Reading from store.txt
		Scanner sc = new Scanner(new File("store.txt"));

		String type = "";
		while (sc.hasNextLine())
		{
			type = sc.nextLine();

			// Differentiating between Song and AudioBook
			if (type.equals("SONG"))
			{
				String id = sc.nextLine();
				String title = sc.nextLine();
				int year = sc.nextInt();
				sc.nextLine();  // Skipping a line after using sc.nextInt()
				int length = sc.nextInt();
				sc.nextLine();
				String artist = sc.nextLine();
				String composer = sc.nextLine();
				String genre = sc.nextLine();

				int lengthOfLyrics = sc.nextInt();
				sc.nextLine();
				String lyrics = "";
				
				// Reading the lyrics 
				for (int i=0; i<lengthOfLyrics; i++)
				{
					lyrics += sc.nextLine() + "\n";	
				}

				// setting the enum type for genre
				System.out.println("LOADING SONG " + title);
				Song.Genre genre2 = Song.Genre.valueOf(genre);
				result.add(new Song(title, year, id, Song.TYPENAME, "", length, artist, composer, genre2, lyrics));
			}

			else if (type.equals("AUDIOBOOK"))
			{
				String id = sc.nextLine();
				String title = sc.nextLine();
				int year = sc.nextInt();
				sc.nextLine();
				int length = sc.nextInt();
				sc.nextLine();
				String author = sc.nextLine();
				String narrator = sc.nextLine();
				
				int numOfChapters = sc.nextInt();
				sc.nextLine();
				ArrayList<String> chapterTitles = new ArrayList<>();
				ArrayList<String> chapters = new ArrayList<>();

				for (int i=0; i<numOfChapters; i++)
				{
					chapterTitles.add(sc.nextLine());	
				}

				int linesInChapter;
				for (int i=0; i<numOfChapters; i++)
				{
					linesInChapter = sc.nextInt();
					sc.nextLine();
					String chapter = "";

					for (int j=0; j<linesInChapter; j++)
					{
						chapter += sc.nextLine();
					}
					
					chapters.add(chapter);
				}
				System.out.println("LOADING AUDIOBOOK " + title);
				result.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
			}
		}
		sc.close();
		return result;
	}

	private HashMap<String, Integer> setTitleMap()
	{
		HashMap<String, Integer> result = new HashMap<>();

		int i = 0;
		for (AudioContent content : contents)
		{
			result.put(content.getTitle(), i);
			i++;
		}

		return result;
	}

	private HashMap<String, ArrayList<Integer>> setArtistMap()
	{
		HashMap<String, ArrayList<Integer>> result = new HashMap<>();

		int i=0;
		for (AudioContent content : contents)
		{
			if (content.getType() == Song.TYPENAME)
			{
				Song song = (Song) content;
				if (!result.containsKey(song.getArtist()))
				{
					ArrayList <Integer> values = new ArrayList<>();
					values.add(i);
					result.put(song.getArtist(), values);
					i++;
				}

				else
				{
					ArrayList <Integer> values = result.get(song.getArtist());
					values.add(i);
					result.put(song.getArtist(), values);
					i++;
				}
			}

			else if (content.getType() == AudioBook.TYPENAME)
			{
				AudioBook book = (AudioBook) content;
				if (!result.containsKey(book.getAuthor()))
				{
					ArrayList <Integer> values = new ArrayList<>();
					values.add(i);
					result.put(book.getAuthor(), values);
					i++;
				}

				else
				{
					ArrayList <Integer> values = result.get(book.getAuthor());
					values.add(i);
					result.put(book.getAuthor(), values);
					i++;
				}
			}
		}

		return result;
	}

	private HashMap<String, ArrayList<Integer>> setGenreMap()
	{
		HashMap<String, ArrayList<Integer>> result = new HashMap<>();
		int i = 0;
		for (AudioContent content : contents)
		{
			if (content.getType() == Song.TYPENAME)
			{
				Song song = (Song) content;
				if (!result.containsKey(song.getGenre().toString()))
				{
					ArrayList <Integer> values = new ArrayList<>();
					values.add(i);
					result.put(song.getGenre().toString(), values);
					i++;
				}

				else
				{
					ArrayList <Integer> values = result.get(song.getGenre().toString());
					values.add(i);
					result.put(song.getGenre().toString(), values);
					i++;
				}
			}
		}

		return result;
	}
}
