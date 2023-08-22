export const ERROR_CONTENT = {
  404: {
    title: '페이지를 찾을 수 없습니다.',
    content: (
      <h2 className="body1 mb-12px">
        주소를 잘못 입력했거나 페이지가 이동했을 수 있습니다.
      </h2>
    ),
  },
  default: {
    title: '현대닷컴 접속이 원활하지 않습니다.',
    content: (
      <>
        <h2 className="body1">
          일시적인 현상이거나, 네트워크 문제일 수 있으니
        </h2>
        <h2 className="body1 mb-12px">잠시 후 다시 시도해주세요.</h2>
      </>
    ),
  },
};
