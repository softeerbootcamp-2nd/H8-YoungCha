import useFetch from '@/hooks/useFetch';
import { TrimsDataType } from './type';
import {
  BasicOptionBox,
  CarsImageBox,
  CarsNameListBox,
  ExteriorColorBox,
  FirstScreenBox,
  GuideModeButton,
  InternalColorBox,
  MainOptionBox,
  MakingCarButtonsBox,
} from '.';

function HomePage() {
  const { data, loading } = useFetch<TrimsDataType>({
    url: '/cars/1/details',
  });

  return (
    <div>
      {!loading && (
        <>
          <FirstScreenBox data={data} />
          <div className="w-full pb-400px">
            <CarsNameListBox trims={data.trims} />
            <div className="flex flex-col items-center pt-32px gap-60px ">
              <CarsImageBox trims={data.trims} />
              <div className="flex flex-col max-w-5xl gap-48px">
                <MainOptionBox trims={data.trims} />
                <ExteriorColorBox trims={data.trims} />
                <InternalColorBox trims={data.trims} />
                <BasicOptionBox trims={data.trims} />
                <MakingCarButtonsBox />
                <GuideModeButton />
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default HomePage;
