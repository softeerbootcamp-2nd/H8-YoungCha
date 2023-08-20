function getPriceTemplete(price: number, isPlus: boolean = false) {
  return `${isPlus ? '+' : ''}${price.toLocaleString()}원`;
}

export default getPriceTemplete;
