export type ColorType = 'exterior' | 'interior';
export type ColorTextType = '외부' | '내부';

export interface OptionType {
  id: number;
  name: string;
  imgUrl: string;
  type?: string;
  categoryId?: number;
  price?: number;
}

export interface OptionGroupType {
  title: string;
  options: Record<string, OptionType>;
}

export interface UserSelectedOptionDataType {
  mainOptions: OptionGroupType;
  colors: OptionGroupType;
  selectedOptions: Omit<OptionGroupType, 'options'> & {
    options: OptionType[];
  };
}
