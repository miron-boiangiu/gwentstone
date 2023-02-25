# GwentStone

## The idea behind this homework

This homework's whole point was to learn about abstraction, inheritance
and how to organize code as to make it as readable as possible, while
also shortening the time it takes to develop a product.

## How the code is organized

My code has the following important classes:

- MainGame - The game's main logic is here, where all the game's
components come together to form the game, and where the players'
actions are parsed and their respective methods are called;
- Table - Where the heroes, the decks, players' hands and the cards
that have been played are kept in, so pretty much where all the cards
are kept for the duration of the game;
- Card - An abstract class which defines the shared characteristics
of all types of cards: environment and minions. An important thing to
note here is that the card information given as input is stored inside
this class, instead of copying everything for no good reason;
- CardFactory - There's a class for each card, because they have
different abilities and characteristics, so the right type of card
is instantiated here based on the input and then returned;
- Deck - This is where the cards of players are kept before they end
up in their hands;
- Minion - Extends Card, adding health, attack damage and other
attributes that only minions have, and environment cards don't;
- EnvironmentCard - Also extends Card, but contains the abstract method
that applies an effect on a row;
- Hero - Contains the remaining health of the hero, its remaining mana
and all the data read from the input.

## The ideas behind some implementation decisions

- There is a separate Class for each card type (Berserker, Firestorm,
The Cursed One, etc.) so as to try and generalize them: if all
special cards have a method called useSpecialAbility, in which each of
their specific abilities is implemented, then I can just call that
method for a card that I know has a special ability instead of making
a bunch of ifs in a single class that they all share.
- The MainGame class follows the Singleton design pattern, because it
makes sense that there only one instance of it is running at all times
and because it makes its usage easier in the other classes. One might
argue that making it Singleton and getting its instance in the other
classes creates undesirable dependencies that make single method
testing more difficult, and, while I agree, these two disadvantages
were tradeoffs I knew about and knowingly implemented, in exchange for
faster development time, since testing didn't check particular
components separately, but instead tested the behaviour of the whole
ensemble.