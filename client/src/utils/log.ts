/**
 * @description
 * 개발 환경에서만 출력되도록 console.log를 wrapping한 로그 출력 함수입니다.
 * `console.log`를 사용하는 함수이기 때문에 `any[]` 타입을 받기 때문에 타입스크립트와 eslint 규칙을 무시합니다.
 *
 * @param messages - 출력할 메시지
 * @returns
 */

/* eslint-disable */
const logger = (...messages: any[]) => {
  if (import.meta.env.PROD) return;
  console.log(...messages);
};

export default logger;
