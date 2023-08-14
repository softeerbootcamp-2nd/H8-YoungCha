import * as Icon from '@/assets/icons';

function LoadingIcons() {
  return (
    <>
      <Icon.CarIcon className="absolute animate-fade w-100px h-100px text-main-blue " />
      <Icon.Spanner
        className="opacity-0 animate-fade"
        style={{ animationDelay: '0.5s' }}
      />
    </>
  );
}

export default LoadingIcons;
