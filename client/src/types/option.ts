export interface AllOptionType {
  id: number;
  categoryId: number;
  checked?: boolean;
  rate: number;
  price: number;
  name: string;
  feedbackTitle: string;
  feedbackDescription: string;
  images: OptionImageType[];
  details: OptionDetailType[];
  tags?: OptionTagType[];
}
export interface AllGuideOptionType extends AllOptionType {
  checked: boolean;
}
export interface OptionImageType {
  imgUrl: string;
  imgType: number;
}

export interface OptionSpecType {
  name: string | null;
  description: string | null;
}

export interface OptionDetailType {
  name?: string | null;
  description?: string | null;
  imgUrl: string | null;
  specs?: OptionSpecType[];
}

export interface OptionTagType {
  rate: number;
  name: string;
}
