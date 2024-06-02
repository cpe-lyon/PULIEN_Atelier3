import { CardInstance } from "./CardInstance";

export class ResponseModel {
  content: CardInstance[];
  pageable: any;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort: any;
  numberOfElements: number;
  first: boolean;
  empty: boolean;

  constructor(
    content: CardInstance[],
    pageable: any,
    last: boolean,
    totalElements: number,
    totalPages: number,
    size: number,
    number: number,
    sort: any,
    numberOfElements: number,
    first: boolean,
    empty: boolean
  ) {
    this.content = content;
    this.pageable = pageable;
    this.last = last;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.size = size;
    this.number = number;
    this.sort = sort;
    this.numberOfElements = numberOfElements;
    this.first = first;
    this.empty = empty;
  }

  static fromJson(json: any): ResponseModel {
    const content = json.content.map((ci: any) => CardInstance.fromJson(ci));
    return new ResponseModel(
      content,
      json.pageable,
      json.last,
      json.totalElements,
      json.totalPages,
      json.size,
      json.number,
      json.sort,
      json.numberOfElements,
      json.first,
      json.empty
    );
  }
}
