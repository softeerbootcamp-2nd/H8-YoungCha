// Typescript를 사용할 경우, vite-env.d.ts 파일을 통해 Vite의 환경 변수의 타입을 지정할 수 있습니다.
// https://vitejs.dev/guide/env-and-mode.html#env-variables

/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_API_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
