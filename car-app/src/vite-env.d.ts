interface ImportMeta {
  readonly env: ImportMetaEnv;
}

interface ImportMetaEnv {
  /*
  항목추가될때마다 추가
  */
    readonly VITE_API_URL: string;

}

