# Sökölogger

## English

Sökö is a poker-like card game.

Sökölogger is a program that allows you to input cards and the program checks which hand wins. The program can also deal cards to the players.

The program first asks the user how many players are in the game, the number of players can be entered when the game starts. After that, the game asks if the players want to input the cards themselves or deal program cards.

The program always plays one hand, to play a new round, the program must be restarted.

Game rules:

1. Cards and players:
   - Sökö is played with a standard 52-card deck without jokers.
   - 2-6 players can participate in the game.

2. Hand rankings:
   - Hand rankings are similar to poker, but Sökö has additional hand options:
   In order from highest to lowest:
   - Straight Flush
   - Four of a Kind
   - Full House
   - Flush
   - Straight
   - Three of a Kind
   - Two Pair
   - Four Flush
   - Four of a Kind
   - One Pair
   - High Card

3. Gameplay:
   - Each player is dealt five cards.
   - Players compare their hands and the highest hand wins.
   - If two players have the same hand, the highest card wins.

4. Tie:
   - If the players have hands of exactly the same value, the game ends in a tie.

5. Card Values:
   - The card values ​​are the same as in poker:
   - Ace (A) is the highest card, followed by King (K), Queen (Q), Jack (J), 10, 9, 8, 7, 6, 5, 4, 3 and 2.

### Command line

The program accepts command line arguments. The following arguments are defined:

| Short | Long      | Description                                                                                                                                                                                                                         |
| ----- | --------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| -h    | --help    | Show the help message                                                                                                                                                                                                               |
| -s    | --simple  | Use simple format when printing to the console                                                                                                                                                                                      |
| -a    | --auto    | Deal cards automatically                                                                                                                                                                                                            |
| -p    | --players | Number of players, can not be used with the -H argument                                                                                                                                                                             |
| -H    | --hands   | Predefined hands to deal to players. Each hand must have five cards separated by a comma. There must be no spaces between each card. You may give multiple hand by separating them with a space or by giving multiple -H arguments. |

## Finnish

Sökö on pokerinkaltainen korttipeli.

Sökölogger on ohjelma, johon voidaan syöttää kortteja ja ohjelma tarkastaa mikä käsi voittaa. Ohjelma voi myös jakaa pelaajille kortteja.

Ohjelma kysyy käyttäjältä ensin kuinka monta pelaajaa on pelissä, pelaajien lukumäärän voi myös syöttää argumenttina pelin käynnistyessä. Sen jälkeen peli kysyy haluaako pelaajat itse syöttää kortit vai jakaako ohjelma kortit.

Ohjelma pelaa aina yhden käden pelatakseen uuden kierroksen tulee ohjelma käynnistää uudelleen. 

Pelin säännöt:

1. Kortit ja pelaajat:
   - Sökö pelataan tavallisella 52 kortin pakalla ilman jokereita.
   - Peliin voi osallistua 2-6 pelaajaa.

2. Käsien arvostus:
   - Käsien arvostus on samanlainen kuin pokerissa, mutta Sökössä on lisäkäsivaihtoehtoja:
     - Värisuora (Straight Flush)
     - Neljä samaa (Four of a Kind)
     - Täyskäsi (Full House)
     - Väri (Flush)
     - Suora (Straight)
     - Kolme samaa (Three of a Kind)
     - Kaksi paria (Two Pair)
     - Nelosväri (Four Flush)
     - Neljän suora (Four straight)
     - Pari (One Pair)  
     - Korkein kortti (High Card)

3. Pelin kulku:
   - Jokaiselle pelaajalle jaetaan viisi korttia.
   - Pelaajat vertaavat käsiään ja korkein käsi voittaa.
   - Jos kaksi pelaajaa saa saman käden, ratkaisee korkein kortti.

4. Tasapeli:
   - Jos pelaajilla on täysin samanarvoiset kädet, peli päättyy tasapeliin.

5. Korttien arvot:
   - Korttien arvot ovat samat kuin pokerissa:
   - Ässä (A) on korkein kortti, jota seuraavat Kuningas (K), Kuningatar (Q), Jätkä (J), 10, 9, 8, 7, 6, 5, 4, 3 ja 2.

### Komentorivi

Ohjelmalle voi antaa komentoriviargumentteja. Seuraavat argumentit hyväksytään:

| Lyhyt | Pitkä     | Selitys                                                                                                                                                                                                                         |
| ----- | --------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| -h    | --help    | Näytä apuviesti                                                                                                                                                                                                                 |
| -s    | --simple  | Näytä tulostus yksinkertaisena                                                                                                                                                                                                  |
| -a    | --auto    | Jaa kortit automaattisesti                                                                                                                                                                                                      |
| -p    | --players | Pelaajien määrä, ei voida käyttää -H argumentin kanssa                                                                                                                                                                          |
| -H    | --hands   | Ennaltamääritetyt jaettavat kädet. Jokainen käsi tulee sisältää viisi korttia pilkuilla erotettuna. Korttien välissä ei saa olla välilyöntejä. Kädet voidaan erottaa toisistaan välilyönnillä tai antamalla usea -H argumentti. |
