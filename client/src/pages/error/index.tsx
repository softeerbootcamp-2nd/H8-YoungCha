import Header from '@/components/Header';
import * as Icon from '@/assets/icons';
import { Link } from 'react-router-dom';
import Button from '@/components/Button';
import getErrorContent from '@/utils/getErrorContent';

interface ErrorPageProps {
  errorType?: string;
}
function ErrorPage({ errorType = 'default' }: ErrorPageProps) {
  const errorContent = getErrorContent(errorType);
  return (
    <>
      <Header />
      <div className="flex flex-col items-center justify-center h-full pt-80px min-w-768px gap-16px">
        <Link to="/">
          <Icon.HDLogo className="w-166px h-23px mb-16px" />
        </Link>

        <h1 className="font-medium font-hsans-head text-32px">
          {errorContent.title}
        </h1>
        {errorContent.content}
        <Link to="/">
          <Button>홈 으로</Button>
        </Link>
      </div>
    </>
  );
}

export default ErrorPage;
