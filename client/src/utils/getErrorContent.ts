import { ERROR_CONTENT } from '@/pages/error/constant';

function getErrorContent(errorType: string) {
  switch (errorType) {
    case '404':
      return ERROR_CONTENT[errorType];
    default:
      return ERROR_CONTENT['default'];
  }
}

export default getErrorContent;
