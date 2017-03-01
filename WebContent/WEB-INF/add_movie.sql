DELIMITER //

CREATE PROCEDURE add_movie(
	IN mTitle VARCHAR(50),
	IN mYear INT,
	IN mDirector VARCHAR(50),
	IN starFirst VARCHAR(50),
	IN starLast VARCHAR(50),
	IN genre VARCHAR(50)
);

process:BEGIN
	declare movieID INT DEFAULT NULL;
	declare starID INT DEFAULT NULL;
	declare genreID INT DEFAULT NULL;
	
	-- check if movie exists
	select id into movieID from movies
	where title = mTitle and
	director = mDirector and
	year = mYear;
	
	-- add movie if it does not exist
	if movieID is null then
	insert into movies(title,year,director) values (mTitle,mYear,mDirector);
	
	-- set movieID to row movie is located in DB
	select id into movieID from movies
	where title = mTitle and
	director = mDirector and
	year = mYear;	
	select 'Movie added to Movies table' as 'm';
	end if;

	if movieID is null then
	LEAVE process;
	end if
	
	select id into starID from stars
	where first_name = starFirst and last_name = starLast;
	
	select id into genreID from genres
	where name = genre;
	
	if starID is null then
	insert into stars(first_name, last_name) values (starFirst,starLast);
	select id into starID from stars
	where first_name = starFirst and
	last_name = starLast;
	select 'Star added to Stars table' as 's';
	end if;

	if genreID is null then
	insert into genres(name) values (genre);
	select id into genreID from genres
	where name = genre;
	select 'Genre added to Genres table' as 'g';
	end if;
	
	if starID is not null then
	insert into stars_in_movies(star_id,movie_id) values (starID,movieID);
	select 'Movie and Star added to stars_in_movies table' as 's_m';
	end if;
	
	if genreID is not null then
	insert into genres_in_movies(genre_id,movie_id) values (genreID,movieID);
	select 'Movie and Genre added to genres_in_movies table' as 'g_m';
	end if;

END
DELIMITER;