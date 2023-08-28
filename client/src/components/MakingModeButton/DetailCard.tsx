import { TrimType } from '@/types';

interface DetailCardProps extends Pick<TrimType, 'name' | 'description'> {
  children?: React.ReactNode;
}

function DetailCard({ children, name, description }: DetailCardProps) {
  return (
    <div className="relative flex items-center text-white glassmorph w-610px h-237px gap-24px p-32px">
      <div className="flex flex-col justify-center flex-1 col-span-5 gap-y-6px">
        <div className="title4">{description}</div>
        <h2 className="font-medium font-hsans-head text-32px">{name}</h2>
      </div>
      <div className="mx-auto flex-2">{children}</div>
    </div>
  );
}

export default DetailCard;
