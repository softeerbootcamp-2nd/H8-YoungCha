/**
 * @description
 * Random 범위 내에서 랜덤한 숫자를 반환합니다.
 */
function randomInRange(min: number, max: number) {
  return Math.random() * (max - min) + min;
}

export { randomInRange };
