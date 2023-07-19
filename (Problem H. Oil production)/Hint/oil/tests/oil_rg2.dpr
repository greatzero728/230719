// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: null

{$R+}
const
	MAX = 300;
	MAXNUM = 1000000;
var 
	a : array [1..MAX, 1..MAX] of longint;
	n, m, i, j, k, s : longint;
	sums : array [0..MAXNUM * 4] of longint;
	sum : longint;
  put: array [1..MAX] of longint;
  cnt : longint;
procedure finish;
begin
	close(input);
	close(output);
	halt(0);
end;
procedure add(num : longint);
begin
  inc(cnt);
  put[cnt] := num;
end;
begin
	reset(input, 'oil.in');
	rewrite(output, 'oil.out');
	read(n, m, s);
	assert((n > 1) and (n <= MAX), 'Wrong n value');
	assert((m > 1) and (m <= MAX), 'Wrong m value');
	assert((s >= 0) and (s <= MAXNUM * 10), 'Wrong sum');
	for i := 1 to n do begin
		for j := 1 to m do begin
			read(a[i][j]);
			assert((a[i][j] >= 0) and (a[i][j] <= MAXNUM), 'Wrong a[i][j] value');
		end;
	end;
	for j := 1 to m - 1 do begin
		for k := j + 1 to m do begin
      		for i := 1 to cnt do begin
        		sums[put[i]] := 0;  
      		end;
      		cnt := 0;
			for i := 1 to n do begin
				sum := a[i][j] + a[i][k];
				if (sum > s) then
					continue;
				if (sums[s - sum] <> 0) then begin
					write(sums[s - sum], ' ', j, ' ', i, ' ', k);
					finish;
				end else begin
          sums[sum] := i;
          add(sum);
        end;
			end;
		end;
	end;
	write(-1);
	finish;
end.
