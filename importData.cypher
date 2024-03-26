// Create constraints
create constraint for (al:Album) require al.id is unique;

create constraint for (ar:Artist) require ar.id is unique;

create constraint for (g:Genre) require g.id is unique;

create constraint for (s:Song) require s.id is unique;

show constraints;

// Load data
load csv WITH headers from "file:///artists.csv" as row
MERGE (ar:Artist { id: row.id })
SET ar.name = row.name,
ar.birthdate = row.birthdate,
ar.url = row.url;

load csv WITH headers from "file:///genres.csv" as row
MERGE (g:Genre { id: row.id })
SET g.name = row.name, g.description = row.description;

load csv WITH headers from "file:///albums.csv" as row
MERGE (al:Album { id: row.id })
MATCH (g:Genre { id: row.genreId })
 SET al.title = row.title,
al.price = toFloat(row.price),
al.yearOfRelease = toInteger(row.yearOfRelease),
al.downloadLink = row.downloadLink
MERGE (al)-[:BELONGS_TO]->(g);

load csv WITH headers from "file:///songs.csv" as row
MERGE (s:Song { id: row.id })
MATCH (a:Album { id: row.albumId })
SET s.name = row.name,
s.runtime = row.runtime,
s.lyric = row.lyric,
s.fileLink = row.fileLink
MERGE (a)-[:HAVE]->(s);

load csv WITH headers from "file:///album_details.csv" as row
MATCH (al:Album { id: row.albumId })
MATCH (ar:Artist { id: row.artistId })
MERGE (al)-[:BE_PERFORMED]->(ar);

// Create relationships