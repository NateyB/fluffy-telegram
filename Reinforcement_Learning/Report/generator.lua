prefixes = {"QLearning", "Sarsa"}
surnames = {"D", "N", "LD", "LN"}
suffixes  = {"(a)", "(b)"}
extension = ".pdf"


for _, p in pairs(prefixes) do
	for _, s in pairs(surnames) do
		for _, su in pairs(suffixes) do
			local filename = p .. s .. su .. extension
			local caption = p
			if (s:match('L')) then
				caption = caption .. ' ($\\lambda{}$)'
			end
			if (s:match('D')) then
				caption = caption .. ' with deterministic behavior ' .. su 
			else
				caption = caption .. ' with nondeterministic behavior ' .. su
			end
			local short
			if (p:match('Q')) then
				short = "Q"
			else
				short = "S"
			end
			short = short .. s
			if (su:match('a')) then
				short = short .. 'A'
			else
				short = short .. 'B'
			end
			print("\\begin{figure}[h]")
			print("\t\\includegraphics[width=0.8\\textwidth]{" .. filename .. "}")
			print("\t\\caption{" .. caption .. "}\\label{fig:" .. short .. "}")
			print("\\end{figure}")
		end
	end
end

