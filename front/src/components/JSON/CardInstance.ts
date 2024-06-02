import { User } from "./User";
import { Card } from "./Card";

export class CardInstance {
  id: number;
  user: User;
  card: Card;
  isBuyable: boolean;

  constructor(id: number, user: User, card: Card, isBuyable: boolean) {
    this.id = id;
    this.user = user;
    this.card = card;
    this.isBuyable = isBuyable;
  }

  static fromJson(json: any): CardInstance {
    return new CardInstance(
      json.id,
      User.fromJson(json.user),
      Card.fromJson(json.card),
      json.isBuyable
    );
  }
}
