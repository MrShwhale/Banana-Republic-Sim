package bananaPackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * This class contains all information about the Republic
 * Each player has thier own Republic, and it has all data about upgrades, money, squares, etc 
 */
public class Republic implements Comparable<Republic>{

	//Constants 
	public static final Republic NONE = new Republic("No one", "White");
	public static final int DEFAULT_GAIN = 1000000;
	private static final double LOAN_PERCENT = 1.25;
	private static final Random R = new Random();

	//Republic cycle info
	private static ArrayList<Republic> republicList = new ArrayList<Republic>();
	private static int currentRepublicIndex = 0;
	private static int currentRound = 1;
	private static int numRounds;
	private static int numTeams;

	//Identity varibles
	private String name;
	private String color;

	//Resource variables
	private int numLandSquares;
	private int numOceanSquares;
	private int numLandAgr;
	private int numLandMine;
	private int numLandFish;
	private int numOceanAgr;
	private int numOceanMine;
	private int numOceanFish;
	private ArrayList<Square> ownedSquares;

	//Financial variables
	private long balance;
	private long loanThisRound;
	private long loanAmount;
	private long loanToWB;

	//'profit' variables (how much money they gained from the resource this round)
	private long mineProfit;
	private long agrProfit;
	private long fishProfit;
	private long fateProfit;

	//Upgrade level variables
	private Development military;
	private Development infrastructure;
	private Development education;
	private Development legal;

	//Upgrade variables
	private boolean squareBought;
	private boolean upgradeBought;

	/* Adding new cards is easy: 
	 	* Add the text to the end of the specified card array
	 	* Add a new case statement in the respective switch statement in the target draw function
	 	* Edit the code within the case to do whatever effect is listed on the card
	 */
	
	//Cards' text arrays
	//Perhaps add a Card enum that holds these?
	//This could cut down on code repetetition a little in drawFate() 
	private static final String[] AGRCARDS = {
			"Terrible drought withers the bananas; you earn no income this round.",
			"Massive flooding destroys the banana crop; you earn no income this round.",

			"Record-breaking crops in other banana-growing countries means the world market is flooded with bananas. The price drops through the floor. Collect $500,000 per agriculture unit.",
			"Infrastructure problems create problems getting your bananas from the farmers to the buyers. Half the crop rots in the trucks. Collect $500,000 per agriculture unit.",

			"Your farmers work nice and hard this round and harvest $1 million per agriculture unit.",
			"All is well in the fields. Collect $1 million per agriculture unit.",
			"Normal rain. Nice sunshine. Naps in the afternoon. Collect $1 million per agriculture unit.",
			"As expected, the rains come as usual and your farmers do the best they can. Collect $1 million per agriculture unit.",
			"Everyone’s happy in the countryside. Collect $1 million per agriculture unit.",
			"Although heavy rains early in the growing season caused worry amongst your farmers, the crops were unaffected. Collect $1 million per agriculture unit.",
			"An extra long growing season seemed likely to bring in extra agriculture income. However, the spread of a banana-eating insect meant an early harvest. Collect $1 million per agriculture unit.",
			"Banana bugs infect your trees and a devastating plague sweeps through the region, destroying half the crop. Although you have fewer bananas, you can sell them at a higher price. Collect $1 million per agriculture unit.",

			"Better than normal weather conditions have given you an extra-good crop. Collect $2 million per agriculture unit.",
			"Use of an experimental fertilizer has increased your crop yield. Collect $2 million per agriculture unit.",
			"A U.N. training program helps your farmers farm more efficiently. Collect $2 million per agriculture unit.",

			"A sudden worldwide demand for bananas means yours are much more valuable. Collect $3 million for each agriculture unit.",
	};

	private static final String[] MINECARDS = {
			"The collapse of a mine shaft kills 37 miners and causes the government to close all mines for mandatory safety inspections. No income can be collected this round.",
			"An earthquake makes conditions for entering mines unsafe; numerous oil platforms are damaged. No income can be collected this round.",

			"A hurricane means no minerals can be extracted from the ocean this round; however, you may collect $1 million from each land-based mineral unit.",

			"An earthquake closes all land-based mineral operations; however, your ocean-based extraction operations collect $1 million per unit.",

			"All’s quiet in the mineral industry this round. Collect $2 million from each mineral unit.",
			"Miners drop down into the shafts without much complaint and send out loads of ore. Collect $2 million from each mineral unit.",
			"The mineral extraction industry quietly chugs along. Collect $2 million from each mineral unit.",
			"The price of oil goes up, but coal goes down. It all balances out. Collect $2 million from each mineral unit.",
			"All the mineral workers are happy and calm. Collect $2 million from each mineral unit.",
			"Safety is up, accidents are down, workers are happy. Collect $2 million from each mineral unit.",
			"A new geological survey speculates you have a large deposit of bauxite in your mountains. Unfortunately these speculations turn out to be false. Collect $2 million from each mineral unit.",
			"Agriculture workers strike demanding better wages and working conditions. However your miners refuse to join them. Collect $2 million from each mineral unit.",

			"Increased demand from Chinese industry causes your mineral income to rise. Collect $4 million from each mineral unit.",
			"Researchers discover that selenium can cure baldness. Increased demand for selenium means you collect $4 million from each mineral unit.",
			"Investment from foreign companies increases your productivity. Miners extract $4 million from each mineral unit.",

			"A worldwide shortage makes the price of oil skyrocket. Collect $6 million from each mineral unit.",
	};
	
	private static final String[] FISHCARDS = {
			"A terrible hurricane prevents your fishing fleet from sailing. Collect no income this round.",
			"El Niño brings warmer water and a shift in ocean currents. Your fishing fleet can find no fish in their usual places. Collect no income this round.",

			"An infrastructure investment means your competitor’s fish arrive to market before yours. When your fish arrive, the market is flooded and you are forced to sell at a reduced price. Collect $500,000 per fishing unit.",
			"One of your fishing vessels breaks down on the high seas and the other ships rush to rescue her crew. Unfortunately, the boat sinks. In addition, due to the time spent on the rescue operation, half your fishing harvest spoils. Collect $500,000 per fishing unit.",

			"Half of your fishing fleet is stuck in the harbor for maintenance. The other half is able to fish, and brings in more than usual. Collect $1 million per fishing unit.",
			"Your fishermen take a crew of trainees out on the sea. They learn pretty well! Collect $1 million per fishing unit.",
			"Just another day on the ocean. Collect $1 million per fishing unit.",
			"Plenty of sun, calm seas, and fat fish. Collect $1 million per fishing unit.",
			"The fishing boats creak out to their usual places and bring in their usual catch. Collect $1 million per fishing unit.",
			"Part of your fishing fleet explores a potential new breeding ground but finds nothing. However, the rest of the fleet brings in a larger-than-normal yield. your Collect $1 million per fishing unit.",
			"It’s a nice, normal round for the fishermen. Collect $1 million per fishing unit.",
			"Calm seas and balmy weather ensure normal fishing conditions. Collect $1 million per fishing unit.",

			"A training session with the UN teaches your fishermen better techniques. If your education system is at least basic, collect $2 million per fishing unit. Otherwise, your fishermen aren’t smart enough to understand and can only collect $1 million per unit.",

			"Nicer-than-normal weather means your fishermen can spend more time on the water. Collect $2 million per fishing unit.",
			"The discovery of a new breeding ground leads to a better-than-normal catch. Collect $2 million per fishing unit.",

			"La Niña brings a stream of nutrient-rich cold water through your territory. Your fishermen report a record harvest. Collect $3 million per fishing unit.",
	};

	private static final String[] UNIMPROVEDFATE = {
			"Foreign investors decide to invest in your unimproved country. However, being an unimproved country, there’s not much to invest in. Collect $1 million per square.",
			"Foreign aid donors decide to give money to your very needy, unimproved country. Collect $100 million",
			"A recent geological survey found traces of a rare mineral in your country. Foreign investors are rushing to explore, bringing loads of money and lots of low-paying jobs to your uneducated people. Collect $2 million extra per mineral unit.",
			"Development of new clean energy technology meets with much rejoicing worldwide. However, it means fewer people want to buy your oil. This leads to job losses and price drops. You lose $1 million per mineral unit.",
			"The UN holds a debt relief conference and decides to forgive indebtedness. All of your debt is cancelled.",
			"The World Bank demands payment of all of your debt. If you cannot pay your debt, all of your cash assets are to be taken and you must pay part of your income each round per agreement with the World Bank.",
			"Bono holds a Farm Relief concert to assist struggling farmers in your country. It’s a huge success, and you receive $5 million per agriculture unit.",
			"International concerns about overfishing cause demonstrators to arrive in your country and protest your fishing industry. Your poorly equipped policemen can hardly handle them, and they manage to blockade your harbors and destroy several of your ships. Lose $2 million per fish unit.",

			"A group of insurgents cause trouble in the mountains. Since your nation is underdeveloped and your military is poorly trained and equipped, this turns into a massively disruptive uprising. Whatever money you should have collected this round is lost.",
			"A radical group of activists holds demonstrations and strikes nationwide to protest conditions for the working people. Since your nation is underdeveloped and your military is poorly trained and equipped, this turns into a massively disruptive event. Whatever money you should have collected this round is lost.",
			"PIRATES are terrorizing your waters. Since your legal system is poorly developed and your law enforcement barely exists, the pirates manage to hijack several of your vessels. Lose $1 million per square of ocean.",
			"The mayor of your capital city was caught running a massive embezzlement scheme involving dozens of public officials and $20 million of public funds. Since your legal system is poorly developed and your court system is a joke, you are unable to prosecute or collect any of the lost money. Lose $20 million.",
			"A devastating hurricane strikes your nation. For each square containing ocean lose $5 million",
			"A recent CNN feature showcased your nation as an undiscovered paradise for tourists. Tourism surges. Collect $5 million per ocean square, $3 million per mountain square, $1 million for all other squares.",
			"A recent investigation found teachers in rural districts not showing up for work. This costs you $1 million per square in wasted money.",
			"Your infrastructure consists of dilapidated schools, crumbling roads, unsafe bridges, and an unstable power supply. You lose $1 million per square",

			"Due to poor discipline in your military system, your nation has become unstable and created a security risk for the region. The regional powers have therefore decided to donate $2 million per square and pay for a military upgrade.",
			"Your lax legal system has made your country a haven for criminals and gangs. Regional powers have therefore decided to donate $1 million per square and pay for a legal upgrade.",
			"A previously undiscovered species of fish is discovered in your waters, and it's beautiful. Collect $3 millon for each ocean square as marine biologists and tourists come from around the world to see it.",
			"Your country’s poor law enforcement has made banking and business difficult, creating difficulties within the region. Regional powers decide to donate $2 million per square.",
			"A period of relative stability in your nation has increased investor confidence. Collect $10 million in foreign investment.",
			"A period of relative stability in your nation has increased investor confidence. Collect $10 million in foreign investment.",
			"Half of your citizens are illiterate. Teachers barely show up for work. The UN has taken pity on your pathetic educational system and pays for an upgrade.",
			"Your citizens lack dependable electricwity, water, and phone service. Internet is a far-off dream. And roads? Mostly dirt and gravel. The World Bank has taken pity on your pathetic infrastructure system and pays for an upgrade.",

			"Your nation's rich oceanic life was recently featured on the Discover Channel. For each ocean square with 2 or 3 fish collect $3 million in tourism dollars.",
			"Your nation's lush jungles were recently declared a world heritage site. For each square with 3 bananas collect $5 million in ecotourism dollars. For each square with 2 bananas collect $3 million in tourism dollars.",
			"A major mining corporation decides to invest in your nation. Since you lack infrastructure, technology, and knowhow, they need to invest heavily. Collect $4 million per mineral unit.",
			"Your nation’s badlands were recently written up in The Lonely Planet as one of the last unspoiled regions on earth. Naturally now everyone wants to go there. For each land square with only one symbol, collect $5 million in tourism dollars.",
			"A major fishing corporation decides to invest in your nation. Since your ports are dilapidated and most of your fleet sadly outdated, this requires a significant amount of investment. Collect $3 million per fish",
			"A major farming corporation decides to invest in your nation. Since most of your farmers are at subsistence level and can barely dream of owning even a burro, let alone a tractor, this requires significant investment. Collect $2 million per banana.",
			"A major earthquake triggers massive landslides in the mountains, a huge tsunami to wreck your coasts, and devastates your infrastructure. Your agriculture, fishing, and mineral industries spend this round rebuilding.",
			"Recent cooperation between you and your neighbor has created warm fuzzy feelings all the way around. Their investors like you, your investors like them, tourism’s up, everyone’s making some money. Collect $10 million in additional investment.",
	};

	private static final String[] BASICFATE = {
			"Foreign investors decide to invest in your country. However, being a country with only basic-level development, there’s not much in which to invest. But they see potential. Collect $10 million per square.",
			"Foreign aid donors decide to give money to your needy, basically developed country. Collect $50 million.",
			"A recent geological survey found traces of a rare mineral in your country. Foreign investors are rushing to explore, bringing in money and jobs for your people. Collect $1 million extra per mineral unit.",
			"Development of new clean energy technology meets with much rejoicing worlwide. However, it means fewer people want to buy your oil, causing job loss and dropping prices. However, since your people have some basic education, they can find other jobs. Losses aren't as bad as they could have been. Lose only $500,000 per mineral unit.",
			"The UN holds a debt relief conference and decides to forgive half of your debt.",
			"The World Bank demands payment of half of your debt. If you cannot pay your debt, all of your cash assets are to be taken and you must sell off natural resource assets per agreement with the World Bank.",
			"Bono holds a Farm Relief concert to assist struggling farmers in your country. He manages to raise $3 million for each agriculture unit in your country.",
			"International concerns about overfishing cause demonstrators to arrive in your country and protest your fishing industry. Your struggling police force manages to keep your fishing vessels safe, but can’t keep them from blockading your harbor, thereby preventing your fishermen from working. Lose $1 million per fishing unit.",

			"A group of insurgents cause trouble in the mountains. Since your country has some basic development, the people aren’t as angry as they could be, and your military can control the situation. Each square of mountainous land can only produce half of its amount.",
			"A radical group of activists holds demonstrations and strikes nationwide to protest conditions for the working people. Since your country has some basic development, the people aren’t as angry as they could be, and your military can control the situation. Your country is only able to produce half of what it should have this round.",
			"PIRATES are terrorizing your waters. Your basic law enforcement system is able to prevent some losses. Lose only $500,000 per square of ocean.",
			"The mayor of your capital city was caught running a massive embezzlement scheme involving dozens of public officials and $20 million of public funds. Your basic legal system is able to prosecute some of the minor players, but the mayor gets away with his ill-gotten goods. You lose $10 million.",
			"A devastating hurricane strikes your nation. For each square containing ocean lose $5 million",
			"A recent CNN feature showcased your nation as an undiscovered paradise for tourists. Tourism surges. Collect $5 million per ocean square, $3 million per mountain square, $1 million for all other squares.",
			"Recent complaints from your citizens and teachers result in demands for a system-wide textbook upgrade. This costs you $500,000 per square.",
			"Your infrastructure isn’t great, but you are able to provide your citizens with dependable roads, power, and communications—even cable TV. No one’s looking to invest, but you’re not losing money either.",

			"Instability in your country due to an underdeveloped military cause concern among regional powers. They offer to pay for a military upgrade for your nation.",
			"Your struggling legal system has created somewhat of a haven for regional crime bosses. The neighbors are concerned and offer to pay for a legal upgrade.",
			"The richest country donates $5 million to you.",
			"Your country’s poor law enforcement has made banking and business difficult. Regional powers decide to help you out and donate $1 mil per square",
			"A period of relative stability in your nation has increased investor confidence. Collect $10 million in foreign investment.",
			"A period of relative stability in your nation has increased investor confidence. Collect $10 million in foreign investment.",
			"The UN has taken pity on your struggling educational system and pays for an upgrade.",
			"The World Bank has taken pity on your tired infrastructure system and pays for an upgrade.",

			"Your nation’s rich oceanic life was recently featured on the Discovery Channel. For each ocean square with 3 fish collect $5 million in tourism dollars. For each ocean square with 2 fish collect $3 million in tourism dollars.",
			"Your nation’s lush jungles were recently declared a world heritage site. For each square with 3 bananas collect $5 million in ecotourism dollars. For each square with 2 bananas collect $3 million in tourism dollars.",
			"A major mining corporation decides to invest in your nation. Since you have some infrastructure in place, you collect $3 million per mineral unit.",
			"Your nation’s badlands were recently written up in The Lonely Planet as one of the last unspoiled regions on earth. Naturally now everyone wants to go there. For each land square with only one symbol, collect $5 million in tourism dollars.",
			"A major fishing corporation decides to invest in your nation. Your fishermen have basic training and some older vessels, but your port is in bad need of an upgrade. Collect $2 million per fish.",
			"A major farming corporation decides to invest in your nation. Most of your farmers have burros, but need tractors. Collect $1 million per banana.",
			"A major earthquake triggers massive landslides in the mountains, a huge tsunami to wreck your coasts, and devastates your infrastructure. Your agriculture, fishing, and mineral industries spend this round rebuilding.",
			"Recent cooperation between you and your neighbor has created warm fuzzy feelings all the way around. Their investors like you, your investors like them, tourism’s up, everyone’s making some money. Collect $10 million in additional investment.",

	};

	private static final String[] ADVANCEDFATE = {
			"Foreign investors decide to invest in your country. As an advanced country that can provide stability as well as opportunity, they feel very safe that they will get a big return on their investment. Collect $50 million per square.",
			"Foreign aid donors decide to give money to your nation, which, being an advanced country, doesn’t need much. Collect a lump sum of $10 million.",
			"A recent geological survey found traces of a rare mineral in your country. Since your people are educated enough to do the research themselves, this doesn’t bring in any foreign investment, but does create some jobs. Collect $500,000 extra per mineral unit.",
			"Development of new clean energy technology meets with much rejoicing worldwide. Even though your people didn’t develop the technology, their advanced education allows them to make some of the parts. Collect $500,000 per mineral unit.",
			"The UN holds a debt relief conference and, as an advanced nation, you agree to forgive some of the debt owed to you. This results in a loss of $20 million.",
			"As an advanced nation, you no longer need to borrow money from the World Bank—instead, they are borrowing money from you, and will pay you interest. Collect five percent of your current balance.",
			"Bono holds a Farm Relief concert to assist struggling farmers in your country. Your farmers aren’t struggling though, but he does come to your country to produce his CDs and make the recording. Collect $5 million.",
			"International concerns about overfishing cause demonstrators to arrive in your country and protest your fishing industry. Your well-trained police force keeps the harbor open and your fishing fleet at sea. Business as usual for your fishermen.",

			"A group of insurgents cause trouble in the mountains—but since your nation is advanced, this is a relatively small group and your military is more than equal to the task. Business proceeds as usual.",
			"A radical group of activists holds demonstrations and strikes nationwide to protest conditions for the working people. However, your advanced citizens have little tolerance for such nonsense, and your well-trained military is able to prevent disruption. Business proceeds as usual.",
			"PIRATES are terrorizing your waters, kidnapping vessels and threatening your ports. Your advanced law enforcement system is able to apprehend most of the pirates, successfully prosecute them in court, and recover all stolen goods. Business proceeds as usual.",
			"The mayor of your capital city was caught running a massive embezzlement scheme involving dozens of public officials and $20 million of public funds. Your advanced legal system is able to bring all guilty parties to justice, however, and recover all stolen funds. Business proceeds as usual.",
			"A devastating hurricane strikes your nation. For each square containing ocean lose $5 million.",
			"A recent CNN feature showcased your nation as an undiscovered paradise for tourists. Tourism surges. Collect $5 million per ocean square, $3 million per mountain square, $1 million for all other squares.",
			"Your educational system was recently rated as one of the best in the world. Thousands of students from foreign countries flock to your excellent universities, bringing their money with them. Collect an extra $10 million.",
			"Recent upgrades to your airport and highways have caused several airlines to make your capital a hub for regional travel. Collect $2 million per square.",

			"The instability of one of your lesser-developed neighbors has become too much of a risk. You and other regional powers decide it is in your best interest to help them out. You volunteer your troops as trainers. This costs you $1 million per square.",
			"The shady legal system of one of your lesser-developed neighbors has created a haven for criminals in the region. You and other regional powers decide to put an end to this by upgrading their legal system. Your cost: $1 million per square.",
			"You donate $5 million to the poorest country",
			"Your country’s well-developed legal system has made banking and business a dream. You’re also constantly being hit up for money by your neighbors. Fortunately the two balance out. You neither gain nor lose income.",
			"A period stability and prosperity in your nation has increased investor confidence. Collect $20 million in foreign investment.",
			"A period of relative stability in your nation has increased investor confidence. Collect $10 million in foreign investment.",
			"The UN has offered to assist your education system by upgrading it. They will pay half if you will pay half, but it must be paid this round.",
			"The World Bank has offered to assist you with an infrastructure grant. They will pay for half an upgrade, but you must pay the other half this round.",

			"Your nation’s rich oceanic life was recently featured on the Discovery Channel. For each ocean square with 3 fish collect $5 million in tourism dollars. For each ocean square with 2 fish collect $3 million in tourism dollars.",
			"Your nation’s lush jungles were recently declared a world heritage site. For each square with 3 bananas collect $5 million in ecotourism dollars. For each square with 2 bananas collect $3 million in tourism dollars.",
			"A major mining corporation decides to invest in your nation. New jobs and equipment purchases result in you collecting $2 mil per mineral unit.",
			"Your nation’s badlands were recently written up in The Lonely Planet as one of the last unspoiled regions on earth. Naturally now everyone wants to go there. For each land square with only one symbol, collect $5 million in tourism dollars.",
			"A major fishing corporation decides to invest in your nation. A fleet upgrade results in you collecting $1 million per fishing unit.",
			"A major farming corporation decides to invest in your nation. Upgrades in equipment result in $500,000 extra per agriculture unit.",
			"A major earthquake triggers massive landslides in the mountains, a huge tsunami to wreck your coasts, and devastates your infrastructure. Your agriculture, fishing, and mineral industries spend this round rebuilding.",
			"Recent cooperation between you and your neighbor has created warm fuzzy feelings all the way around. Their investors like you, your investors like them, tourism’s up, everyone’s making some money. Collect $50 million in additional investment.",

	};

	private static final String[] HITECHFATE = {

			"As a world power and hi-tech nation with a stable government, your currency attracts numerous foreign investors. Collect $100 million per square.",
			"The UN asks you to host a conference on hunger relief. As one of the world’s foremost developed nations you are expected to give a lot. Donate $20 million per square.",
			"A recent geological survey conducted by one of your leading universities found traces of a rare mineral in your country. Spend $10 million per mineral unit to explore this resource.",
			"Development of new clean energy technology meets with much rejoicing worldwide. Since your nation developed this technology, you are rolling in the dough. Collect $10 million per mineral unit.",
			"The UN holds a debt relief conference and, as one of the world’s leading creditor nations, looks to you for leadership. You set an example by forgiving all basic and undeveloped nations that owe you money. This costs you $100 million.",
			"As a creditor nation, the World Bank borrows money from you—and pays you interest to do so. Collect 10% of your current balance.",
			"Bono holds a Farm Relief concert to assist struggling farmers in your country. However, since he’s from your country and your farmers don’t need it, you collect nothing . Instead, your citizens donate $200 million, which you record as a net loss.",
			"International concerns about overfishing cause demonstrators to arrive in your country and protest your fishing industry. Your police not only prevent disruption, but prove your nation stable enough to host the next World Cup. Collect $100 million in tourism income.",

			"Environmentalists put pressure on the government to preserve forest area. Half of your agricultural land is now preserved as a natural park and cannot be farmed.",
			"Your government decides to develop a space program. Invest $300 million in this new project.",
			"PIRATES are terrorizing your waters. Your expert police and military not only nab and jail the pirates, they recover all stolen merchandise. This security makes your ports a favored destination for global traders & cruise ships. Collect an extra $10 million per square of ocean.",
			"The mayor of your capital city was caught running a massive embezzlement scheme involving dozens of public officials and $20 million of public funds. The public is outraged and refuses to tolerate such corruption. All parties are successfully prosecuted, and all stolen monies are recovered. In addition, the government seizes all of the former mayor’s ill-gotten personal assets, gaining you a cool $5 million—and the respect of your neighbors.",
			"A devastating hurricane strikes your nation. For each square containing ocean lose $5 million",
			"With pristine coral reefs and rare fish in danger from possible oil spills, you close down half of your ocean-based operations.",
			"Your world-class educational system has produced some of the world’s brightest and best minds. In addition to attracting students from all over the world, numerous corporations are relocating their headquarters and research facilities in your nation. Collect an extra $50 million.",
			"Your world-class airport, excellent transportation system, and hi-tech communications systems make you an ideal destination for television stations and call centers looking to establish regional headquarters. Collect $5 million per square in new investments.",

			"The instability of one of your lesserdeveloped neighbors has become too much of a risk. You and other regional powers decide it is in your best interest to help them out. You volunteer your troops as trainers and donate new military equipment. This costs you $20 million per square.",
			"The shady legal system of one of your lesser-developed neighbors has created a safe haven for one of the region’s most powerful crime bosses. You launch an invasion and successfully nab him, bringing him back to your country for prosecution. This costs you $20 million per square.",
			"As a nation of caring, global citizens, you take the initiative in helping to relieve global poverty by giving each: Unimproved nation: $300 million Basic nation: $100 million Advanced nation: $50 million",
			"Your country’s outstanding legal system has made banking and business a dream—and it’s made you very wealthy. You’re also constantly being hit up for money by your neighbors. Donate $20 million per square.",
			"One of your major cities is selected to host the Olympics. While you spend over $1 billion on new stadiums and infrastructure improvements, the income brought by tourists and new business opportunities gains you $150 million in new investments.",
			"Your prosperous, stable society is a haven for investors. Collect $20 million per square in foreign investment.",
			"As one of the world’s most developed nations, you take the leadership in a new education initiative. Choose a neighbor and pay to upgrading their education system.",
			"As one of the world’s most developed nations, you must lead the way in making the world a better place. You also realize that better infrastructure in your neighbor means more business opportunities. Choose a neighbor and pay the cost of upgrading their infrastructure.",

			"Your nation’s rich oceanic life was recently featured on the Discovery Channel. For each ocean square with 3 fish collect $10 million in tourism dollars. For each ocean square with 2 fish collect $5 million in tourism dollars.",
			"Your nation’s lush jungles were recently declared a world heritage site. For each square with 3 bananas collect $10 million in ecotourism dollars. For each square with 2 bananas collect $5 million in tourism dollars",
			"At a conference on global warming you sign a treaty limiting your carbon emissions and greenhouse gases. Cut the number of mineral units you currently have in half.",
			"Your nation’s badlands were recently written up in The Lonely Planet as one of the last unspoiled regions on earth. Naturally now everyone wants to go there. For each land square with only one symbol, collect $50 million in tourism dollars.",
			"Concerns about overfishing cause you to sign a treaty restricting your fishing. Cut the number of fishing units you currently have in half.",
			"Your farmers develop a new genetically modified strain of banana which, unfortunately, the rest of the world refuses to buy. Lose half of your banana income this round.",
			"A major earthquake triggers massive landslides in the mountains, a huge tsunami to wreck your coasts, and devastates your infrastructure. Your agriculture, fishing, and mineral industries are reduced to half this round.",
			"You and your neighbor sign a free trade deal, flinging wide the doors for new investments. Collect $150 million.",

	};

	public Republic(String name, String color)
	{
		this.name = name;
		this.color = color;

		numLandSquares = 0;
		numOceanSquares = 0;
		numLandAgr = 0;
		numLandMine = 0;
		numLandFish = 0;
		numOceanAgr = 0;
		numOceanMine = 0;
		numOceanFish = 0;

		ownedSquares = new ArrayList<Square>();

		balance = 0;
		loanAmount = 0;
		loanToWB = 0;

		military = Development.UNIMPROVED;
		infrastructure = Development.UNIMPROVED;
		education = Development.UNIMPROVED;
		legal = Development.UNIMPROVED;
	}

	//Constructor for getRichest/PoorestRepublic
	public Republic(long startingMoney)
	{
		this.balance = startingMoney;
	}

	public static int getCurrentRepublicIndex()
	{
		return currentRepublicIndex;
	}

	public static Republic getCurrentRepublic()
	{
		return republicList.get(currentRepublicIndex);
	}

	public static void incrementCurrentRepublic()
	{
		currentRepublicIndex++;
	}

	public static void setCurrentRepublic(int newCurrent)
	{
		currentRepublicIndex = newCurrent;
	}

	public static void addRepublic(Republic r)
	{
		republicList.add(r);
		//incrementCurrentRepublic();
	}

	public static boolean isDuplicateRepublic(Republic r)
	{
		return Republic.republicList.contains(r);
	}

	/**
	 * Adds a square to the list of squares that the Republic owns
	 * Also adds to resource values and square number values
	 * 
	 * @param s - The square to add
	 */
	public void addSquare(Square s)
	{
		if(s.isLand())
		{
			numLandAgr += s.getAgr();
			numLandMine += s.getMine();
			numLandFish += s.getFish();
			numLandSquares++;
		}
		else
		{
			numOceanAgr += s.getAgr();
			numOceanMine += s.getMine();
			numOceanFish += s.getFish();
			numOceanSquares++;
		}
		ownedSquares.add(s);
	}

	public int getNumSquares()
	{
		return numOceanSquares + numLandSquares;
	}

	public int getNumMine()
	{
		return numLandMine+numOceanMine;
	}

	public int getNumAgr()
	{
		return numLandAgr+numOceanAgr;
	}

	public int getNumFish()
	{
		return numLandFish+numOceanFish;
	}

	/**
	 * This is for all draw<blank>Card() methods
	 * Picks a random card, and does the effect it says on the card
	 *
	 * @return the text of the card
	 */

	public String drawFateCard()
	{
		fateProfit = 0;

		String cardText;

		int random;
		
		//After 10 rounds, revolution cards can be added
		//1 revolution card is added for each maxloan*5 in debt, both in negative money and to the WB
		//Therefore, (money-loanAmount)/(5*maxLoan) is the revolution mod
		//But only if it is 
		long realBalance = balance - loanAmount;
		
		int revMod = (int) ((currentRound > 10 && realBalance < 0) ? -realBalance/(5*getDevelopment().getMaxLoan()*DEFAULT_GAIN) : 0);

		//Get a fate card text based on development level
		switch(getDevelopment())
		{
			case UNIMPROVED:
				//Get a random prompt and set that to be the return text
				//The prompt should have extra numbers equal to number of revolution cards
				
				random = R.nextInt(UNIMPROVEDFATE.length + revMod);
				
				try{cardText = UNIMPROVEDFATE[random];}
				catch(IndexOutOfBoundsException OOB) 
				{return revolution();}

				//Do the effects listed on the card
				switch(random)
				{
					case 0:
						fateProfit += getNumSquares()*DEFAULT_GAIN;
						break;
					case 1:
						fateProfit += 100*DEFAULT_GAIN;
						break;
				 	case 2:
						fateProfit += 2*getMine()*DEFAULT_GAIN;
						break;
					case 3:
						fateProfit -= getMine()*DEFAULT_GAIN;
						break;
					case 4:
						loanAmount = 0;
						break;
					case 5:
						balance -= loanAmount;
						loanAmount = 0;
						
						if(balance < 0)
						{
							loanToWB = -balance;
							balance = 0;
						}
						break;
					case 6:
						fateProfit += 5*getAgr()*DEFAULT_GAIN;
						break;
					case 7:
						fateProfit -= 2*getFish()*DEFAULT_GAIN;
						break;
					case 8:
					case 9:
						fateProfit -= agrProfit + mineProfit + fishProfit;

						agrProfit = 0;
						mineProfit = 0;
						fishProfit = 0;
						break;
					case 10:
						fateProfit -= numOceanSquares*DEFAULT_GAIN;
						break;
					case 11:
						fateProfit -= 20*DEFAULT_GAIN;
						break;
					case 12:
						fateProfit -= 5*numOceanSquares*DEFAULT_GAIN;
						break;
					case 13:
					for (Square ownedSquare : ownedSquares) {
						if(!ownedSquare.isLand())
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.isMountain())
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
						else
						{
							fateProfit += DEFAULT_GAIN;
						}
					}
						break;
					case 14:
					case 15:
						fateProfit -= getNumSquares()*DEFAULT_GAIN;
						break;
					case 16:
						
						//This sort of thing should only trigger if the development of the republic 
						
						fateProfit += 2*getNumSquares()*DEFAULT_GAIN;
						if(military.getIntLevel() <= getDevelopment().getIntLevel())
						{
							military = military.nextLevel();
						}
						break;
					case 17:
						fateProfit += getNumSquares()*DEFAULT_GAIN;
						legal = legal.nextLevel();
						if(legal.getIntLevel() >= getDevelopment().getIntLevel())
						{
							legal = legal.nextLevel();
						}
						break;
					case 18:
						fateProfit += 3*numOceanSquares*DEFAULT_GAIN;
						break;
					case 19:
						fateProfit += 2*getNumSquares()*DEFAULT_GAIN;
						break;
					case 20:
					case 21:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 22:
						if(education.getIntLevel() >= getDevelopment().getIntLevel())
						{
							education = education.nextLevel();
						}
						break;
					case 23:
						if(infrastructure.getIntLevel() >= getDevelopment().getIntLevel())
						{
							infrastructure = infrastructure.nextLevel();
						}
						break;
					case 24:
						ownedSquares.forEach((s) -> { fateProfit += (s.getFish() == 2 || s.getFish() == 3) ? 2*DEFAULT_GAIN : 0; });
						break;
					case 25:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getAgr() == 2)
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
						else if (ownedSquare.getAgr() == 3)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
					}
						break;
					case 26:
						fateProfit += 4*getMine()*DEFAULT_GAIN;
						break;
					case 27:
						ownedSquares.forEach((s) -> { fateProfit += (s.getTotalResources() == 1) ? 5*DEFAULT_GAIN : 0; });
						break;
					case 28:
						fateProfit += 4*getFish()*DEFAULT_GAIN;
						break;
					case 29:
						fateProfit += 2*getAgr()*DEFAULT_GAIN;
						break;
					case 30:
						fateProfit -= mineProfit + agrProfit + fishProfit;

						mineProfit = 0;
						agrProfit = 0;
						fishProfit = 0;
						break;
					case 31:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					default:
						return revolution();
				}
				break;
			case BASIC:
				//Get a random prompt and set that to be the return text
				random = R.nextInt(BASICFATE.length + revMod);
				try{cardText = BASICFATE[random];}
				catch(IndexOutOfBoundsException OOB) 
				{return revolution();}
				//Do the effects listed on the card
				switch(random)
				{
					case 0:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 1:
						fateProfit += 50*DEFAULT_GAIN;
						break;
					case 2:
						fateProfit += getMine()*DEFAULT_GAIN;
						break;
					case 3:
						fateProfit -= (int)(0.5*getMine()*DEFAULT_GAIN);
						break;
					case 4:
						loanAmount /= 2;
						break;
					case 5:
						balance -= loanAmount/2;
						loanAmount /= 2;
						
						if(balance < 0)
						{
							loanToWB = -balance;
							balance = 0;
						}
						break;
					case 6:
						fateProfit += 3*getAgr()*DEFAULT_GAIN;
						break;
					case 7:
						fateProfit -= 3*getFish()*DEFAULT_GAIN;
						break;
					case 8:
						//Algorithm to run this one:
						//((Total profit from each resource / DEFAULT_GAIN) / number of the resource) = price per resource
						//For every square, check if it is a mountain, then use this to subtract some from fateProfit
						
						long fishPrice = (fishProfit/getFish());
						long minePrice = (mineProfit/getMine());
						long agrPrice = (agrProfit/getAgr());
						
						for(int i = 0; i < ownedSquares.size(); i++)
						{
							if(ownedSquares.get(i).isMountain())
							{
								agrProfit -= ownedSquares.get(i).getAgr()*agrPrice/2;
								fateProfit -= ownedSquares.get(i).getAgr()*agrPrice/2;
								
								fishProfit -= ownedSquares.get(i).getFish()*fishPrice/2;
								fateProfit -= ownedSquares.get(i).getFish()*fishPrice/2;
								
								mineProfit -= ownedSquares.get(i).getMine()*minePrice/2;
								fateProfit -= ownedSquares.get(i).getMine()*minePrice/2;
							}
						}
						
						break;
					case 9:
						fateProfit -= mineProfit/2 + agrProfit/2 + fishProfit/2;

						mineProfit /= 2;
						agrProfit /= 2;
						fishProfit /= 2;
						break;
					case 10:
						fateProfit -=(int)(0.5*numOceanSquares*DEFAULT_GAIN);
						break;
					case 11:
						fateProfit -= 10*DEFAULT_GAIN;
						break;
					case 12:
						fateProfit -= 10*numOceanSquares*DEFAULT_GAIN;
						break;
					case 13:
					for (Square ownedSquare : ownedSquares) {
						if(!ownedSquare.isLand())
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.isMountain())
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
						else
						{
							fateProfit += DEFAULT_GAIN;
						}
					}
						break;
					case 14:
						fateProfit -=(int)(0.5*getNumSquares()*DEFAULT_GAIN);
						break;
					case 15:
						//No effect
						break;
					case 16:
						if(military.getIntLevel() >= getDevelopment().getIntLevel())
						{
							military = military.nextLevel();
						}
						break;
					case 17:
						if(legal.getIntLevel() >= getDevelopment().getIntLevel())
						{
							legal = legal.nextLevel();
						}
						break;
					case 18:
						getRichestRepublic().addMoney(-5*DEFAULT_GAIN);
						fateProfit += 5*DEFAULT_GAIN;
						break;
					case 19:
						fateProfit += getNumSquares()*DEFAULT_GAIN;
						break;
					case 20:
					case 21:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 22:
						if(education.getIntLevel() >= getDevelopment().getIntLevel())
						{
							education = education.nextLevel();
						}
						break;
					case 23:
						if(infrastructure.getIntLevel() >= getDevelopment().getIntLevel())
						{
							infrastructure = infrastructure.nextLevel();
						}
						break;
					case 24:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getFish() == 3)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.getFish() == 2)
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
					}
						break;
					case 25:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getAgr() == 3)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.getAgr() == 2)
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
					}
						break;
					case 26:
						fateProfit += 3*getMine()*DEFAULT_GAIN;
						break;
					case 27:
						for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getTotalResources() == 1)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
					}
						break;
					case 28:
						fateProfit += 2*getNumFish()*DEFAULT_GAIN;
						break;
					case 29:
						fateProfit += getNumAgr()*DEFAULT_GAIN;
						break;
					case 30:
						fateProfit -= mineProfit + agrProfit + fishProfit;

						mineProfit = 0;
						agrProfit = 0;
						fishProfit = 0;
						break;
					case 31:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					default:
						return revolution();
				}
				break;
			case ADVANCED:
				//Get a random prompt and set that to be the return text
				random = R.nextInt(ADVANCEDFATE.length + revMod);
				try{cardText = ADVANCEDFATE[random];}
				catch(IndexOutOfBoundsException OOB) 
				{return revolution();}

				//Do the effects listed on the card
				switch(random)
				{
					case 0:
						fateProfit += 50*DEFAULT_GAIN;
						break;
					case 1:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 2:
					case 3:
						fateProfit += (int)(0.5*getMine()*DEFAULT_GAIN);
						break;
					case 4:
						fateProfit -= 20*DEFAULT_GAIN;
						break;
					case 5:
						fateProfit = (int)(balance * 0.05);
						break;
					case 6:
						fateProfit += 5*DEFAULT_GAIN;
						break;
					case 7:
						//No effect
					case 8:
						//No effect
					case 9:
						//No effect
					case 10:
						//No effect
					case 11:
						//No effect
						break;
					case 12:
						fateProfit -= 5*numOceanSquares*DEFAULT_GAIN;
						break;
					case 13:
					for (Square ownedSquare : ownedSquares) {
						if(!ownedSquare.isLand())
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.isMountain())
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
						else
						{
							fateProfit += DEFAULT_GAIN;
						}
					}
						break;
					case 14:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 15:
						fateProfit += 2*getNumSquares()*DEFAULT_GAIN;
						break;
					case 16:
					case 17:
						fateProfit -= getNumSquares()*DEFAULT_GAIN;
						break;
					case 18:
						getPoorestRepublic().addMoney(5*DEFAULT_GAIN);
						fateProfit -= 5*DEFAULT_GAIN;
						break;
					case 19:
						//No effect
						break;
					case 20:
						fateProfit += 20*DEFAULT_GAIN;
						break;
					case 21:
						fateProfit += 10*DEFAULT_GAIN;
						break;
					case 22:
						//education
						if(education.getIntLevel() >= getDevelopment().getIntLevel() || education == Development.HITECH)
						{
							education = education.nextLevel();
							fateProfit -= education.getUpgradeCost()*DEFAULT_GAIN/2;
						}
						break;
					case 23:
						//infrastructure
						if(infrastructure.getIntLevel() >= getDevelopment().getIntLevel() || infrastructure == Development.HITECH)
						{
							infrastructure = infrastructure.nextLevel();
							fateProfit -= infrastructure.getUpgradeCost()*DEFAULT_GAIN/2;
						}
						break;
					case 24:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getFish() == 3)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
						else if (ownedSquare.getFish() == 2)
						{
							fateProfit += 3*DEFAULT_GAIN;
						}
					}
						break;
					case 25:
						for (Square ownedSquare : ownedSquares) 
						{
							if(ownedSquare.getAgr() == 3)
							{
								fateProfit += 5*DEFAULT_GAIN;
							}
							else if (ownedSquare.getAgr() == 2)
							{
								fateProfit += 3*DEFAULT_GAIN;
							}
						}
						break;
					case 26:
						fateProfit += 2*getMine()*DEFAULT_GAIN;
						break;
					case 27:
						for (Square ownedSquare : ownedSquares) {
							if(ownedSquare.getTotalResources() == 1)
							{
								fateProfit += 5*DEFAULT_GAIN;
							}
						}
						break;
					case 28:
						fateProfit += getFish()*DEFAULT_GAIN;
						break;
					case 29:
						fateProfit += 0.5*getAgr()*DEFAULT_GAIN;
						break;
					case 30:
						fateProfit -= mineProfit + agrProfit + fishProfit;

						mineProfit = 0;
						agrProfit = 0;
						fishProfit = 0;
						break;
					case 31:
						fateProfit += 50*DEFAULT_GAIN;
						break;
					default:
						return revolution();
				}
				break;
			default:
				//Get a random prompt and set that to be the return text
				random = R.nextInt(HITECHFATE.length + revMod);
				try{cardText = HITECHFATE[random];}
				catch(IndexOutOfBoundsException OOB) 
				{return revolution();}

				//Do the effects listed on the card
				switch(random)
				{
					case 0:
						fateProfit += 100*getNumSquares()*DEFAULT_GAIN;
						break;
					case 1:
						fateProfit -= 20*getNumSquares()*DEFAULT_GAIN;
						break;
					case 2:
						fateProfit -= 10*getMine()*DEFAULT_GAIN;
						break;
					case 3:
						fateProfit += 10*getMine()*DEFAULT_GAIN;
						break;
					case 4:
						fateProfit -= 100*DEFAULT_GAIN;
						break;
					case 5:
						fateProfit += (int)(balance * 0.1);
						break;
					case 6:
						fateProfit -= 200*DEFAULT_GAIN;
						break;
					case 7:
						fateProfit += 100*DEFAULT_GAIN;
						break;
					case 8:
						numLandAgr /= 2;
						numOceanAgr /= 2;
						break;
					case 9:
						fateProfit -= 300*DEFAULT_GAIN;
						break;
					case 10:
						fateProfit += 10*numOceanSquares*DEFAULT_GAIN;
						break;
					case 11:
						fateProfit += 5*DEFAULT_GAIN;
						break;
					case 12:
						fateProfit -= 5*numOceanSquares*DEFAULT_GAIN;
						break;
					case 13:
						numOceanFish /= 2;
						numOceanAgr /= 2;
						numOceanMine /= 2;
						break;
					case 14:
						fateProfit -= 50*DEFAULT_GAIN;
						break;
					case 15:
						fateProfit += 5*getNumSquares()*DEFAULT_GAIN;
						break;
					case 16:
					case 17:
						fateProfit -= 20*getNumSquares()*DEFAULT_GAIN;
						break;
					case 18:
					for (Republic element : republicList) {
						int donate = 0;
						switch(element.getDevelopment())
						{
						case UNIMPROVED:
							donate = 300*DEFAULT_GAIN;
							break;
						case BASIC:
							donate = 100*DEFAULT_GAIN;
							break;
						case ADVANCED:
							donate = 50*DEFAULT_GAIN;
							break;
						default:
							//No effect
							break;
						}
						balance -= donate;
						element.addMoney(donate);
					}
						break;
					case 19:
						fateProfit -= 20*getNumSquares()*DEFAULT_GAIN;
						break;
					case 20:
						fateProfit += 150*DEFAULT_GAIN;
						break;
					case 21:
						fateProfit += 20*getNumSquares()*DEFAULT_GAIN;
						break;
					case 22:
						//Get the Republic with the lowest education
						Republic leastEducated = new Republic(1);
						leastEducated.setEducation(Development.HITECH);
						for (Republic element : republicList) {
							if (element.getEducationLevel().getIntLevel() < leastEducated.getEducationLevel().getIntLevel())
							{
								leastEducated = element;
							}
						}
						
						//Don't try to upgrade someone at full level
						if(leastEducated.getEducationLevel() == Development.HITECH)
						{
							break;
						}
						
						leastEducated.setEducation(leastEducated.getEducationLevel());
						
						balance -= leastEducated.getEducationLevel().getUpgradeCost() * leastEducated.getNumSquares() * DEFAULT_GAIN;
						break;
					case 23:						
						//Get the Republic with the lowest infrastructure
						Republic leastInfrastructure = new Republic(1);
						leastInfrastructure.setInfrastructure(Development.HITECH);
						for (Republic element : republicList) {
							if (element.getInfrastructureLevel().getIntLevel() < leastInfrastructure.getInfrastructureLevel().getIntLevel())
							{
								leastInfrastructure = element;
							}
						}
						
						//Don't try to upgrade someone at full level
						if(leastInfrastructure.getInfrastructureLevel() == Development.HITECH)
						{
							break;
						}
						
						leastInfrastructure.setInfrastructure(leastInfrastructure.getInfrastructureLevel());
						
						balance -= leastInfrastructure.getInfrastructureLevel().getUpgradeCost() * leastInfrastructure.getNumSquares() * DEFAULT_GAIN;
						break;
					case 24:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getFish() == 3)
						{
							fateProfit += 10*DEFAULT_GAIN;
						}
						else if (ownedSquare.getFish() == 2)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
					}
						break;
					case 25:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getAgr() == 3)
						{
							fateProfit += 10*DEFAULT_GAIN;
						}
						else if (ownedSquare.getAgr() == 2)
						{
							fateProfit += 5*DEFAULT_GAIN;
						}
					}
						break;
					case 26:
						numOceanMine /= 2;
						numLandMine /= 2;
						break;
					case 27:
					for (Square ownedSquare : ownedSquares) {
						if(ownedSquare.getTotalResources() == 1)
						{
							fateProfit += 50*DEFAULT_GAIN;
						}
					}
						break;
					case 28:
						numOceanFish /= 2;
						numLandFish /= 2;
						break;
					case 29:
						fateProfit -= agrProfit/2;

						agrProfit /= 2;
						break;
					case 30:
						fateProfit -= mineProfit/2 + agrProfit/2 + fishProfit/2;

						mineProfit /= 2;
						agrProfit /= 2;
						fishProfit /= 2;
						break;
					case 31:
						fateProfit += 150*DEFAULT_GAIN;
						break;
					default:
						return revolution();
				}
				break;
		}
		return cardText;
	}

	public String drawMineCard()
	{
		mineProfit = 0;

		int cardIndex = R.nextInt(MINECARDS.length);

		double multiplier = 1.0;

		//Controls if land/ocean squares are counted for money this round
		boolean landBased = true;
		boolean oceanBased = true;

		switch (cardIndex)
		{
			case 0:
				multiplier = 0.0;
				break;
			case 1:
				multiplier = 0.0;
				break;
			case 2:
				multiplier = 1.0;
				oceanBased = false;
				break;
			case 3:
				multiplier = 1.0;
				landBased = false;
				break;
			case 4:
				multiplier = 2.0;
				break;
			case 5:
				multiplier = 2.0;
				break;
			case 6:
				multiplier = 2.0;
				break;
			case 7:
				multiplier = 2.0;
				break;
			case 8:
				multiplier = 2.0;
				break;
			case 9:
				multiplier = 2.0;
				break;
			case 10:
				multiplier = 2.0;
				break;
			case 11:
				multiplier = 2.0;
				break;
			case 12:
				multiplier = 4.0;
				break;
			case 13:
				multiplier = 4.0;
				break;
			case 14:
				multiplier = 4.0;
				break;
			case 15:
				multiplier = 6.0;
				break;
		}

		mineProfit = (int)(((oceanBased ? numOceanMine : 0) +
				(landBased ? numLandMine : 0)) * multiplier) * DEFAULT_GAIN;

		return MINECARDS[cardIndex];
	}

	public String drawAgrCard()
	{
		agrProfit = 0;

		int cardIndex = R.nextInt(AGRCARDS.length);

		double multiplier = 1.0;

		//Controls if land/ocean squares are counted for money this round
		boolean landBased = true;
		boolean oceanBased = true;

		switch (cardIndex)
		{
		case 0:
		case 1:
			multiplier = 0.0;
			break;
		case 2:
		case 3:
			multiplier = 0.5;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			multiplier = 1.0;
			break;
		case 12:
		case 13:
		case 14:
			multiplier = 2.0;
			break;
		case 15:
			multiplier = 3.0;
			break;
		}

		agrProfit = (int)(((oceanBased ? numOceanAgr : 0) +
				(landBased ? numLandAgr : 0)) * multiplier) * DEFAULT_GAIN;

		return AGRCARDS[cardIndex];
	}

	public String drawFishCard()
	{
		fishProfit = 0;

		int cardIndex = R.nextInt(FISHCARDS.length);

		double multiplier = 1.0;

		//Controls if land/ocean squares are counted for money this round
		boolean landBased = true;
		boolean oceanBased = true;

		switch (cardIndex)
		{
		case 0:
			multiplier = 0.0;
			break;
		case 1:
			multiplier = 0.0;
			break;
		case 2:
			multiplier = 0.5;
			break;
		case 3:
			multiplier = 0.5;
			break;
		case 4:
			multiplier = 1.0;
			break;
		case 5:
			multiplier = 1.0;
			break;
		case 6:
			multiplier = 1.0;
			break;
		case 7:
			multiplier = 1.0;
			break;
		case 8:
			multiplier = 1.0;
			break;
		case 9:
			multiplier = 1.0;
			break;
		case 10:
			multiplier = 1.0;
			break;
		case 11:
			multiplier = 1.0;
			break;
		case 12:
			multiplier = education == Development.UNIMPROVED ? 1.0 : 2.0 ;
			break;
		case 13:
			multiplier = 2.0;
			break;
		case 14:
			multiplier = 2.0;
			break;
		case 15:
			multiplier = 3.0;
			break;
		}

		fishProfit = (int)(((oceanBased ? numOceanFish : 0) +
				(landBased ? numLandFish : 0)) * multiplier) * DEFAULT_GAIN;

		return FISHCARDS[cardIndex];
	}

	/**
	 * This is for all add<blank>Profit() methods
	 * Increases the Republic's money by the profit value 
	 */
	
	public void addMineProfit()
	{
		if(loanToWB > 0)
		{
			loanToWB -= mineProfit / 2;
			if (loanToWB < 0)
			{
				mineProfit -= loanToWB;
			}
		}
		balance += mineProfit;
	}

	public void addFishProfit()
	{
		if(loanToWB > 0)
		{
			loanToWB -= fishProfit / 2;
			if (loanToWB < 0)
			{
				fishProfit -= loanToWB;
			}
		}
		balance += fishProfit;
	}

	public void addAgrProfit()
	{
		if(loanToWB > 0)
		{
			loanToWB -= agrProfit / 2;
			if (loanToWB < 0)
			{
				agrProfit -= loanToWB;
			}
		}
		balance += agrProfit;
	}

	public void addFateProfit()
	{
		balance += fateProfit;
	}

	public void addMoney(long l)
	{
		balance += l;
	}

	public void setMoney(long amount)
	{
		balance = amount;
	}

	public void takeLoan(int takeAmount)
	{
		//No need to rewrite these safegaurds
		if(takeAmount <= 0)
		{
			payLoan(-takeAmount);
		}
		
		takeAmount *= DEFAULT_GAIN;

		loanThisRound = takeAmount;
		loanAmount += takeAmount;
		balance += takeAmount;
	}

	public void payLoan(int payAmount)
	{
		//No need to write more safegaurds
		if(payAmount < 0)
		{
			takeLoan(-payAmount);
		}
		else if(payAmount == 0)
		{
			return;
		}

		payAmount *= DEFAULT_GAIN;

		//You can't pay a loan that would put you into negative money
		if(balance < payAmount)
		{
			//This is a safe conversion, because amount has to be within the integer limit
			//So if money is less than it, long->int is lossess and therefore safe
			payAmount = (int) balance;
		}

		//You can't pay the loan more than it is
		if(loanAmount < payAmount)
		{
			//Same rationale as above
			payAmount = (int) loanAmount;
		}

		loanAmount -= payAmount;
		balance -= payAmount;
	}

	/**
	 * Does the revolution effect: no money gain this round
	 * @return The revolution text 
	 */
	private String revolution()
	{
		fateProfit -= mineProfit + agrProfit + fishProfit;

		mineProfit = 0;
		agrProfit = 0;
		fishProfit = 0;
		return "REVOLUTION! Whatever money you were to have collected this round is forfeited since your government has been overthrown.";
	}

	/**
	 * Makes 3 comparisons to find the minimum prod value
	 * Then returns the Development opposite of that value
	 *
	 * @return the minimum level of development for an upgrade (the republic development level)
	 */
	public Development getDevelopment()
	{
		return Development.getDevelopmentFromLevel(Math.min(
			Math.min(military.getIntLevel(), legal.getIntLevel()),
			Math.min(education.getIntLevel(), infrastructure.getIntLevel())));
	}

	/**
	 * Simulates the system costs for a certain number of extra squares
	 * Can simulate system cost for fewer squares if numNewSquares is negative
	 * @param numAdditionalSquares number of additional squares to predict the cost of
	 * @return the predected costs
	 */
	public int getSystemCosts(int numAdditionalSquares)
	{
		return calculateCosts(getNumSquares() + numAdditionalSquares, military, legal, education, infrastructure);
	}

	/**
	 * Simulates the system costs for a certain setup of upgrades
	 *
	 * @param the levels for the stats
	 *
	 * @return the predected costs
	 */

	public int getSystemCosts(Development mil, Development leg, Development edu, Development inf)
	{
		return calculateCosts(getNumSquares(), mil, leg, edu, inf);
	}

	public int getSystemCosts()
	{
		return calculateCosts(getNumSquares(), military, legal, education, infrastructure);
	}

	/**
	 * Calculates the system costs based on number of squares and level of upgrades
	 * This algorithm is based on the original spreadsheet
	 *
	 * @param squares - number of owned squares in the situation
	 * @param mil - level of military in this situation
	 * @param leg - level of legal stat
	 * @param edu - level of education stat
	 * @param inf - level of infrastructure stat
	 * @return System costs with given stats
	 */
	private int calculateCosts(int squares, Development mil, Development leg, Development edu, Development inf)
	{
		return (int)((Math.pow(mil.getIntLevel(), 2) * squares * DEFAULT_GAIN) +
				(int)(Math.pow(leg.getProductivity(), 2) * squares * DEFAULT_GAIN) +
				(int)(Math.pow(edu.getProductivity(), 2) * squares * DEFAULT_GAIN) +
				(int)(Math.pow(inf.getProductivity(), 2) * squares * DEFAULT_GAIN));
	}

	public Development getMilitaryLevel()
	{
		return military;
	}

	public void setMilitary(Development military) {
		this.military = military;
	}

	public Development getEducationLevel()
	{
		return education;
	}

	public void setEducation(Development education) {
		this.education = education;
	}

	public Development getLegalLevel()
	{
		return legal;
	}

	public void setLegal(Development legal) {
		this.legal = legal;
	}

	public Development getInfrastructureLevel()
	{
		return infrastructure;
	}

	public void setInfrastructure(Development infrastructure) {
		this.infrastructure = infrastructure;
	}

	public static int getCurrentRound()
	{
		return currentRound;
	}

	public static void incrementRound()
	{
		currentRound++;
	}

	@Override
	public int compareTo(Republic o)
	{
		return this.name.compareTo(o.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Republic other = (Republic) obj;
		return Objects.equals(name.toLowerCase(), other.name.toLowerCase());
	}

	public static int getRounds() {
		return numRounds;
	}

	public static void setRounds(int numRounds) {
		Republic.numRounds = numRounds;
	}

	public static int getTeams() {
		return numTeams;
	}

	public static void setTeams(int numTeams) {
		Republic.numTeams = numTeams;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return BananaRepublic.stringToColor(color);
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getAgr()
	{
		return numLandAgr+numOceanAgr;
	}

	public int getMine()
	{
		return numLandMine+numOceanMine;
	}

	public int getFish()
	{
		return numLandFish+numOceanFish;
	}

	public long getMoney()
	{
		return balance;
	}

	/**
	 * Increases the loan amount by the LOAN_PERCENT
	 */
	public void addLoanInterest()
	{
		loanAmount *= 1+(LOAN_PERCENT/100.0);
	}

	public long getLoanAmount()
	{
		return loanAmount;
	}
	
	public long getLoanToWB()
	{
		return loanToWB;
	}
	
	public int getMaxLoan()
	{
		return (int) (getDevelopment().getMaxLoan());
	}

	public String getColorText() {
		return color;
	}

	public long getMineProfit() {
		return mineProfit;
	}

	public void setMineProfit(long mineProfit) {
		this.mineProfit = mineProfit;
	}

	public long getAgrProfit() {
		return agrProfit;
	}

	public void setAgrProfit(long agrProfit) {
		this.agrProfit = agrProfit;
	}

	public long getFishProfit() {
		return fishProfit;
	}

	public void setFishProfit(long fishProfit) {
		this.fishProfit = fishProfit;
	}

	public long getFateProfit() {
		return fateProfit;
	}

	public void setFateProfit(long fateProfit) {
		this.fateProfit = fateProfit;
	}

	public long getTotalResourceProfit()
	{
		return agrProfit + fishProfit + mineProfit;
	}

	public double getProductivity()
	{
		return ((int)((legal.getProductivity()*education.getProductivity()*infrastructure.getProductivity())*1000))/1000.0;
	}

	/**
	 * @return The Republic which has the least money (counting debt + WB debt)
	 */
	public static Republic getPoorestRepublic()
	{
		Republic poorest = new Republic(Long.MAX_VALUE);
		for (Republic element : republicList) {
			if (element.getMoney() - element.getLoanAmount() - element.getLoanToWB() < poorest.getMoney())
			{
				poorest = element;
			}
		}

		return poorest;
	}

	/**
	 * @return The Republic which has the most money (counting debt + WB debt)
	 */
	public static Republic getRichestRepublic()
	{
		Republic richest = new Republic(Long.MIN_VALUE);
		for (Republic element : republicList) {
			if (element.getMoney() - element.getLoanAmount() - element.getLoanToWB() > richest.getMoney())
			{
				richest = element;
			}
		}

		return richest;
	}

	public boolean isSquareBought() {
		return squareBought;
	}

	public void setSquareBought(boolean squareBought) {
		this.squareBought = squareBought;
	}

	public boolean isUpgradeBought() {
		return upgradeBought;
	}

	public void setUpgradeBought(boolean upgradeBought) {
		this.upgradeBought = upgradeBought;
	}

	public long getLoanThisRound() {
		return loanThisRound;
	}

}
