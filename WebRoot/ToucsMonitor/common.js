function trim(src){
	if(src==null)
		return src;
	start=src.search(' ');
	end=src.length;
	for(i=end;i>0;i--){
		if(src.charAt(i-1)!=' ')
			break;
		end--;
	}
	return src.substring(start,end);
}
