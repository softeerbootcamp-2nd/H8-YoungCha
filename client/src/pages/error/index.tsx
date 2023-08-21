import Header from '@/components/Header';
import * as Icon from '@/assets/icons';
import { Link } from 'react-router-dom';
import Button from '@/components/Button';

function ErrorPage() {
  return (
    <>
      <Header />
      <div className="flex flex-col items-center justify-center h-full pt-80px min-w-768px gap-16px">
        <Link to="/">
          <Icon.HDLogo className="w-166px h-23px mb-16px" />
        </Link>

        <h1 className="font-medium font-hsans-head text-32px">
          현대닷컴 접속이 원활하지 않습니다.
        </h1>
        <h2 className="body1">
          일시적인 현상이거나, 네트워크 문제일 수 있으니
        </h2>
        <h2 className="body1 mb-12px">잠시 후 다시 시도해주세요.</h2>
        <Link to="/">
          <Button>홈 으로</Button>
        </Link>
      </div>
    </>
  );
}

export default ErrorPage;
