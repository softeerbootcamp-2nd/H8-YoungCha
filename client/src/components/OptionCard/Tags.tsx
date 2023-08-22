import { OptionType } from '@/types/option';

interface TagsProps extends Pick<OptionType, 'tags'> {}

function Tags({ tags }: TagsProps) {
  return tags?.map((item, index) => (
    <div
      key={`tags-${index}`}
      className="flex items-center ml-8px body3 bg-tag-skyblue px-8px rounded-2px text-main-blue"
    >
      {`${item.name} ${item.rate}%`}
    </div>
  ));
}

export default Tags;
