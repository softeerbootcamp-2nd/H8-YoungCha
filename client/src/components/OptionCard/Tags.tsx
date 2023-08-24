import { AllOptionType } from '@/types/option';

interface TagsProps extends Pick<AllOptionType, 'tags'> {}

function Tags({ tags }: TagsProps) {
  return tags?.map((tag, index) => (
    <div
      key={`tags-${index}`}
      className="flex items-center body3 bg-tag-skyblue px-8px rounded-2px text-main-blue"
    >
      {`${tag.name}  ${tag.rate}%`}
    </div>
  ));
}

export default Tags;
