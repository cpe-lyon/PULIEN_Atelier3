export class Card {
    cardId: number;
    name: string;
    image: string | null;
    nation: string;
    pace: number;
    weight: number;
    height: number;
    price: number;
    rating: number;

    constructor(cardId: number, name: string, image: string | null, nation: string, pace: number, weight: number, height: number, price: number, rating: number) {
        this.cardId = cardId;
        this.name = name;
        this.image = image;
        this.nation = nation;
        this.pace = pace;
        this.weight = weight;
        this.height = height;
        this.price = price;
        this.rating = rating;
    }

    static fromJson(json: any): Card {
        return new Card(
            json.cardId,
            json.name,
            json.image,
            json.nation,
            json.pace,
            json.weight,
            json.height,
            json.price,
            json.rating
        );
    }
}
