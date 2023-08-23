function padWithZero(num: number, len: number): string {
  return num.toString().padStart(len, '0');
}

export default padWithZero;
