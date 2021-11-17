
const key = 'draft';

export function saveDraft(article: any){
    localStorage.setItem(key,JSON.stringify(article));
}

