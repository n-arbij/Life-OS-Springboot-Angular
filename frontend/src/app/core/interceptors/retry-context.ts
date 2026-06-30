import { HttpContextToken } from '@angular/common/http';

export const RETRY_REQUEST = new HttpContextToken<boolean>(() => false);