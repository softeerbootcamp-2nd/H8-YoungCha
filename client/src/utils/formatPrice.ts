function formatPrice(price: number, isPlus: boolean = false) {
  return `${isPlus ? '+' : ''}${Intl.NumberFormat('ko-KR').format(price)}원`;
}

export default formatPrice;
